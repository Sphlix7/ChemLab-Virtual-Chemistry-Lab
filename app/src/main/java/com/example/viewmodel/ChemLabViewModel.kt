package com.example.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ChemLabRepository
import com.example.data.ChemicalData
import com.example.data.ExperimentData
import com.example.data.GamificationData
import com.example.data.PeriodicTableData
import com.example.data.QuizData
import com.example.data.ReactionData
import com.example.data.db.ChemDatabase
import com.example.data.db.LabNotebookEntryEntity
import com.example.data.db.UserProgressEntity
import com.example.model.ApparatusItem
import com.example.model.Chemical
import com.example.model.ChemicalElement
import com.example.model.ChemicalReaction
import com.example.model.ChemistryNote
import com.example.model.Experiment
import com.example.model.QuizQuestion
import com.example.util.VoiceNarrator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.log10

enum class AppScreen {
  HOME,
  EXPERIMENTS,
  VIRTUAL_LAB,
  GUIDED_EXP,
  REACTION_SIM,
  TITRATION_SIM,
  CALCULATOR,
  PERIODIC_TABLE,
  NOTES,
  QUIZ,
  SAFETY,
  NOTEBOOK
}

class ChemLabViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: ChemLabRepository
  private val voiceNarrator: VoiceNarrator = VoiceNarrator(application)
  val rewardedAdManager: com.example.util.RewardedAdManager = com.example.util.RewardedAdManager(application)

  val adStatus = rewardedAdManager.adStatus
  val adStatusMessage = rewardedAdManager.statusMessage

  init {
    val db = ChemDatabase.getDatabase(application)
    repository = ChemLabRepository(db.labDao())
    rewardedAdManager.initialize()
  }

  fun showRewardedAd(activity: android.app.Activity, onRewardEarnedCallback: (() -> Unit)? = null) {
    rewardedAdManager.showRewardedAd(
      activity = activity,
      onRewardEarned = { amount, type ->
        viewModelScope.launch {
          repository.addXp(50)
        }
        onRewardEarnedCallback?.invoke()
      }
    )
  }

  fun reloadRewardedAd() {
    rewardedAdManager.loadRewardedAd()
  }

  // Navigation Screen Backstack
  private val _screenStack = MutableStateFlow(listOf(AppScreen.HOME))
  val currentScreen: StateFlow<AppScreen> = MutableStateFlow(AppScreen.HOME).apply {
    viewModelScope.launch {
      _screenStack.collect { stack ->
        value = stack.lastOrNull() ?: AppScreen.HOME
      }
    }
  }

  fun navigateTo(screen: AppScreen) {
    val current = _screenStack.value
    if (current.lastOrNull() != screen) {
      _screenStack.value = current + screen
    }
  }

  fun navigateBack(): Boolean {
    val current = _screenStack.value
    if (current.size > 1) {
      _screenStack.value = current.dropLast(1)
      return true
    }
    return false
  }

  // Repository Data Flows
  val notebookEntries: StateFlow<List<LabNotebookEntryEntity>> = repository.notebookEntries
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val userProgress: StateFlow<UserProgressEntity> = repository.userProgress
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProgressEntity())

  // Guided Experiment Runner
  private val _selectedExperiment = MutableStateFlow<Experiment>(ExperimentData.experiments[0])
  val selectedExperiment = _selectedExperiment.asStateFlow()

  private val _guidedStepIndex = MutableStateFlow(0)
  val guidedStepIndex = _guidedStepIndex.asStateFlow()

  private val _isStepActionDone = MutableStateFlow(false)
  val isStepActionDone = _isStepActionDone.asStateFlow()

  private val _isVoiceEnabled = MutableStateFlow(true)
  val isVoiceEnabled = _isVoiceEnabled.asStateFlow()

  fun startGuidedExperiment(exp: Experiment) {
    _selectedExperiment.value = exp
    _guidedStepIndex.value = 0
    _isStepActionDone.value = false
    navigateTo(AppScreen.GUIDED_EXP)
    readCurrentStep()
  }

  fun nextGuidedStep() {
    val exp = _selectedExperiment.value
    if (_guidedStepIndex.value < exp.steps.size - 1) {
      _guidedStepIndex.value += 1
      _isStepActionDone.value = false
      readCurrentStep()
    } else {
      // Completed experiment!
      viewModelScope.launch {
        repository.markExperimentCompleted(exp.id)
      }
    }
  }

  fun prevGuidedStep() {
    if (_guidedStepIndex.value > 0) {
      _guidedStepIndex.value -= 1
      _isStepActionDone.value = false
      readCurrentStep()
    }
  }

  fun performGuidedStepAction() {
    _isStepActionDone.value = true
  }

  fun toggleVoice() {
    val newVal = !_isVoiceEnabled.value
    _isVoiceEnabled.value = newVal
    if (!newVal) {
      voiceNarrator.stop()
    } else {
      readCurrentStep()
    }
  }

  private fun readCurrentStep() {
    if (_isVoiceEnabled.value) {
      val exp = _selectedExperiment.value
      val step = exp.steps.getOrNull(_guidedStepIndex.value)
      if (step != null) {
        voiceNarrator.speak("Step ${step.stepNumber}: ${step.title}. ${step.instruction}")
      }
    }
  }

  // ===================== Virtual Lab Workbench State =====================
  private val _labApparatus = MutableStateFlow<ApparatusItem>(ChemicalData.apparatusList[0])
  val labApparatus = _labApparatus.asStateFlow()

  private val _labChemicalsInVessel = MutableStateFlow<List<Chemical>>(emptyList())
  val labChemicalsInVessel = _labChemicalsInVessel.asStateFlow()

  private val _labLiquidColor = MutableStateFlow(Color(0x3338BDF8))
  val labLiquidColor = _labLiquidColor.asStateFlow()

  private val _labLiquidLevel = MutableStateFlow(0.0f)
  val labLiquidLevel = _labLiquidLevel.asStateFlow()

  private val _labTemp = MutableStateFlow(22.0)
  val labTemp = _labTemp.asStateFlow()

  private val _labPh = MutableStateFlow(7.0)
  val labPh = _labPh.asStateFlow()

  private val _isLabHeating = MutableStateFlow(false)
  val isLabHeating = _isLabHeating.asStateFlow()

  private val _hasLabBubbles = MutableStateFlow(false)
  val hasLabBubbles = _hasLabBubbles.asStateFlow()

  private val _hasLabPrecipitate = MutableStateFlow(false)
  val hasLabPrecipitate = _hasLabPrecipitate.asStateFlow()

  private val _labPrecipitateColor = MutableStateFlow(Color.Yellow)
  val labPrecipitateColor = _labPrecipitateColor.asStateFlow()

  private val _labCurrentReaction = MutableStateFlow<ChemicalReaction?>(null)
  val labCurrentReaction = _labCurrentReaction.asStateFlow()

  private val _labStatusMessage = MutableStateFlow("Select chemicals from the shelf to add to the vessel.")
  val labStatusMessage = _labStatusMessage.asStateFlow()

  private var heatingJob: Job? = null

  fun setLabApparatus(apparatus: ApparatusItem) {
    _labApparatus.value = apparatus
  }

  fun addChemicalToLab(chemical: Chemical) {
    val current = _labChemicalsInVessel.value.toMutableList()
    current.add(chemical)
    _labChemicalsInVessel.value = current

    // Increase liquid volume level
    _labLiquidLevel.value = (_labLiquidLevel.value + 0.22f).coerceAtMost(0.85f)

    // Blend color
    _labLiquidColor.value = Color(chemical.colorHex)

    // Calculate updated pH
    val avgPh = current.map { it.ph }.average()
    _labPh.value = avgPh

    // Check for chemical reactions
    val ids = current.map { it.id }
    val reaction = ReactionData.findReaction(ids)
    if (reaction != null) {
      _labCurrentReaction.value = reaction
      _labLiquidColor.value = Color(reaction.resultingColorHex)
      _labStatusMessage.value = "Reaction occurred: ${reaction.equation}"

      if (reaction.gasProduced != null) {
        _hasLabBubbles.value = true
      }
      if (reaction.precipitateColor != null) {
        _hasLabPrecipitate.value = true
        _labPrecipitateColor.value = when (reaction.precipitateColor) {
          "Golden Yellow" -> Color(0xFFFACC15)
          "Chalky White", "Curdy White (Silver Chloride)" -> Color(0xFFF1F5F9)
          else -> Color(0xFFB45309)
        }
      }
      if (reaction.tempChange.contains("+")) {
        _labTemp.value += 12.0
      }
      viewModelScope.launch {
        repository.addXp(25)
      }
    } else {
      _labStatusMessage.value = "Added ${chemical.name} (${chemical.formula}) to vessel."
    }
  }

  fun toggleLabHeating() {
    val heating = !_isLabHeating.value
    _isLabHeating.value = heating
    if (heating) {
      heatingJob?.cancel()
      heatingJob = viewModelScope.launch {
        while (_isLabHeating.value && _labTemp.value < 105.0) {
          delay(400)
          _labTemp.value += 2.5
          if (_labTemp.value >= 75.0 && _labLiquidLevel.value > 0.05f) {
            _hasLabBubbles.value = true
          }
        }
      }
    } else {
      heatingJob?.cancel()
      heatingJob = viewModelScope.launch {
        while (!_isLabHeating.value && _labTemp.value > 25.0) {
          delay(800)
          _labTemp.value = (_labTemp.value - 1.5).coerceAtLeast(22.0)
          if (_labTemp.value < 60.0 && _labCurrentReaction.value?.gasProduced == null) {
            _hasLabBubbles.value = false
          }
        }
      }
    }
  }

  fun resetLabWorkbench() {
    heatingJob?.cancel()
    _isLabHeating.value = false
    _labChemicalsInVessel.value = emptyList()
    _labLiquidLevel.value = 0.0f
    _labLiquidColor.value = Color(0x3338BDF8)
    _labTemp.value = 22.0
    _labPh.value = 7.0
    _hasLabBubbles.value = false
    _hasLabPrecipitate.value = false
    _labCurrentReaction.value = null
    _labStatusMessage.value = "Workbench cleared and glassware washed."
  }

  fun logCurrentObservationToNotebook(noteText: String) {
    viewModelScope.launch {
      val chems = _labChemicalsInVessel.value.joinToString(", ") { "${it.name} (${it.formula})" }
      val obs = _labCurrentReaction.value?.explanation ?: "Mixed substances in ${_labApparatus.value.name}. Final pH: ${"%.1f".format(_labPh.value)}, Temp: ${"%.1f".format(_labTemp.value)}°C."
      repository.saveNotebookEntry(
        title = "Virtual Lab Experiment",
        chemicals = if (chems.isEmpty()) "Water" else chems,
        observations = obs,
        temp = _labTemp.value,
        ph = _labPh.value,
        notes = noteText
      )
    }
  }

  // ===================== Titration Simulator State =====================
  private val _titrantAddedMl = MutableStateFlow(0f)
  val titrantAddedMl = _titrantAddedMl.asStateFlow()

  private val _isTitrationTapOpen = MutableStateFlow(false)
  val isTitrationTapOpen = _isTitrationTapOpen.asStateFlow()

  private val _titrationPh = MutableStateFlow(1.0f)
  val titrationPh = _titrationPh.asStateFlow()

  private val _titrationFlaskColor = MutableStateFlow(Color(0x3338BDF8))
  val titrationFlaskColor = _titrationFlaskColor.asStateFlow()

  private val _titrationCurveData = MutableStateFlow(listOf(Pair(0f, 1.0f)))
  val titrationCurveData = _titrationCurveData.asStateFlow()

  private val _isEndpointReached = MutableStateFlow(false)
  val isEndpointReached = _isEndpointReached.asStateFlow()

  private var titrationJob: Job? = null

  fun toggleTitrationTap() {
    val isOpen = !_isTitrationTapOpen.value
    _isTitrationTapOpen.value = isOpen

    if (isOpen) {
      titrationJob?.cancel()
      titrationJob = viewModelScope.launch {
        while (_isTitrationTapOpen.value && _titrantAddedMl.value < 50f) {
          delay(200)
          addTitrantVolume(0.5f)
        }
        if (_titrantAddedMl.value >= 50f) {
          _isTitrationTapOpen.value = false
        }
      }
    } else {
      titrationJob?.cancel()
    }
  }

  fun addSingleDrop() {
    addTitrantVolume(0.1f)
  }

  private fun addTitrantVolume(amount: Float) {
    val newVol = (_titrantAddedMl.value + amount).coerceAtMost(50f)
    _titrantAddedMl.value = newVol

    // Titration pH calculation for 25 mL of 0.1 M HCl titrated with 0.1 M NaOH
    // Equivalence point is at 25.0 mL!
    val eqVol = 25.0f
    val currentPh = when {
      newVol < eqVol - 0.2f -> {
        // Excess acid
        val remainingMoles = (0.1 * (eqVol - newVol) / 1000.0)
        val totalVolL = (25.0 + newVol) / 1000.0
        val hConc = (remainingMoles / totalVolL).coerceAtLeast(1e-7)
        (-log10(hConc)).toFloat()
      }
      abs(newVol - eqVol) <= 0.2f -> {
        // Sharp inflection at equivalence
        7.0f + (newVol - eqVol) * 10f
      }
      else -> {
        // Excess base
        val excessMoles = (0.1 * (newVol - eqVol) / 1000.0)
        val totalVolL = (25.0 + newVol) / 1000.0
        val ohConc = (excessMoles / totalVolL).coerceAtLeast(1e-7)
        (14.0 - (-log10(ohConc))).toFloat().coerceAtMost(13.2f)
      }
    }.coerceIn(1.0f, 13.5f)

    _titrationPh.value = currentPh

    // Update Phenolphthalein color
    _titrationFlaskColor.value = when {
      currentPh < 8.2f -> Color(0x3338BDF8) // Colorless / clear
      currentPh in 8.2f..9.5f -> Color(0x99F472B6) // Faint pale pink blush (ENDPOINT!)
      else -> Color(0xFFDB2777) // Intense deep magenta
    }

    if (abs(newVol - eqVol) <= 0.3f && !_isEndpointReached.value) {
      _isEndpointReached.value = true
      viewModelScope.launch {
        repository.addXp(40)
      }
    }

    val history = _titrationCurveData.value.toMutableList()
    history.add(Pair(newVol, currentPh))
    _titrationCurveData.value = history
  }

  fun resetTitration() {
    titrationJob?.cancel()
    _isTitrationTapOpen.value = false
    _titrantAddedMl.value = 0f
    _titrationPh.value = 1.0f
    _titrationFlaskColor.value = Color(0x3338BDF8)
    _isEndpointReached.value = false
    _titrationCurveData.value = listOf(Pair(0f, 1.0f))
  }

  // ===================== Quiz State =====================
  private val _quizQuestions: MutableStateFlow<List<QuizQuestion>> = MutableStateFlow(QuizData.questions)
  val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

  private val _currentQuizIndex = MutableStateFlow(0)
  val currentQuizIndex = _currentQuizIndex.asStateFlow()

  private val _selectedOption = MutableStateFlow<Int?>(null)
  val selectedOption = _selectedOption.asStateFlow()

  private val _isQuizSubmitted = MutableStateFlow(false)
  val isQuizSubmitted = _isQuizSubmitted.asStateFlow()

  private val _quizScore = MutableStateFlow(0)
  val quizScore = _quizScore.asStateFlow()

  private val _quizFinished = MutableStateFlow(false)
  val quizFinished = _quizFinished.asStateFlow()

  fun selectQuizOption(index: Int) {
    if (!_isQuizSubmitted.value) {
      _selectedOption.value = index
    }
  }

  fun submitQuizAnswer() {
    if (_selectedOption.value == null) return
    _isQuizSubmitted.value = true

    val q = _quizQuestions.value.getOrNull(_currentQuizIndex.value)
    if (q != null && _selectedOption.value == q.correctIndex) {
      _quizScore.value += 1
      viewModelScope.launch {
        repository.addXp(20)
      }
    }
  }

  fun nextQuizQuestion() {
    if (_currentQuizIndex.value < _quizQuestions.value.size - 1) {
      _currentQuizIndex.value += 1
      _selectedOption.value = null
      _isQuizSubmitted.value = false
    } else {
      _quizFinished.value = true
      viewModelScope.launch {
        repository.addXp(50) // completion bonus
      }
    }
  }

  fun restartQuiz() {
    _currentQuizIndex.value = 0
    _selectedOption.value = null
    _isQuizSubmitted.value = false
    _quizScore.value = 0
    _quizFinished.value = false
  }

  override fun onCleared() {
    super.onCleared()
    voiceNarrator.shutdown()
  }
}
