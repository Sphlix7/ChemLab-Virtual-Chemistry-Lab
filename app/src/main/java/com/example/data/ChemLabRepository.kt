package com.example.data

import com.example.data.db.LabDao
import com.example.data.db.LabNotebookEntryEntity
import com.example.data.db.UserProgressEntity
import com.example.model.BadgeItem
import com.example.model.Chemical
import com.example.model.ChemicalElement
import com.example.model.ChemicalReaction
import com.example.model.ChemistryNote
import com.example.model.Experiment
import com.example.model.QuizQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ChemLabRepository(private val labDao: LabDao) {

  val notebookEntries: Flow<List<LabNotebookEntryEntity>> = labDao.getAllNotebookEntries()
  val userProgress: Flow<UserProgressEntity> = labDao.getUserProgress().map { it ?: UserProgressEntity() }

  fun getExperiments(): List<Experiment> = ExperimentData.experiments
  fun getChemicals(): List<Chemical> = ChemicalData.chemicals
  fun getApparatus() = ChemicalData.apparatusList
  fun getReactions(): List<ChemicalReaction> = ReactionData.reactions
  fun getElements(): List<ChemicalElement> = PeriodicTableData.elements
  fun getNotes(): List<ChemistryNote> = NotesData.notes
  fun getQuizQuestions(): List<QuizQuestion> = QuizData.questions
  fun getBadges(): List<BadgeItem> = GamificationData.badges

  suspend fun saveNotebookEntry(
    title: String,
    chemicals: String,
    observations: String,
    temp: Double,
    ph: Double,
    notes: String
  ): Long {
    val entry = LabNotebookEntryEntity(
      experimentTitle = title,
      chemicalsUsed = chemicals,
      observations = observations,
      tempCelsius = temp,
      phMeasured = ph,
      notes = notes
    )
    val id = labDao.insertNotebookEntry(entry)
    addXp(30)
    return id
  }

  suspend fun deleteNotebookEntry(id: Long) {
    labDao.deleteNotebookEntry(id)
  }

  suspend fun addXp(amount: Int) {
    val current = labDao.getUserProgress().firstOrNull() ?: UserProgressEntity()
    val newXp = current.xp + amount
    labDao.insertOrUpdateProgress(current.copy(xp = newXp))
  }

  suspend fun markExperimentCompleted(expId: String) {
    val current = labDao.getUserProgress().firstOrNull() ?: UserProgressEntity()
    val list = parseJsonList(current.completedExperimentsJson).toMutableSet()
    if (!list.contains(expId)) {
      list.add(expId)
      val newJson = list.joinToString(prefix = "[\"", separator = "\",\"", postfix = "\"]")
      val newXp = current.xp + 50
      labDao.insertOrUpdateProgress(current.copy(
        completedExperimentsJson = newJson,
        xp = newXp
      ))
    }
  }

  private fun parseJsonList(json: String): List<String> {
    return json.trim().removeSurrounding("[", "]")
      .split(",")
      .map { it.trim().removeSurrounding("\"") }
      .filter { it.isNotEmpty() }
  }
}
