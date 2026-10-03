package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.ChemistryCalculatorScreen
import com.example.ui.screens.ExperimentLibraryScreen
import com.example.ui.screens.GuidedExperimentScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LabNotebookScreen
import com.example.ui.screens.NotesScreen
import com.example.ui.screens.PeriodicTableScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ReactionSimulatorScreen
import com.example.ui.screens.SafetyScreen
import com.example.ui.screens.TitrationSimulatorScreen
import com.example.ui.screens.VirtualLabScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.ChemLabViewModel

class MainActivity : ComponentActivity() {

  private val viewModel: ChemLabViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    // Initialize Google Mobile Ads SDK for AdMob
    com.google.android.gms.ads.MobileAds.initialize(this) {}
    setContent {
      MyApplicationTheme {
        ChemLabApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun ChemLabApp(viewModel: ChemLabViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()

  // Handle system back navigation
  BackHandler(enabled = currentScreen != AppScreen.HOME) {
    viewModel.navigateBack()
  }

  val isBottomNavVisible = currentScreen in listOf(
    AppScreen.HOME,
    AppScreen.EXPERIMENTS,
    AppScreen.VIRTUAL_LAB,
    AppScreen.TITRATION_SIM,
    AppScreen.PERIODIC_TABLE
  )

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .windowInsetsPadding(WindowInsets.safeDrawing),
    bottomBar = {
      if (isBottomNavVisible) {
        NavigationBar(
          modifier = Modifier.testTag("chemlab_bottom_navigation"),
          containerColor = MaterialTheme.colorScheme.surface,
          tonalElevation = 8.dp
        ) {
          NavigationBarItem(
            selected = currentScreen == AppScreen.HOME,
            onClick = { viewModel.navigateTo(AppScreen.HOME) },
            icon = { Text("🏠", fontSize = 18.sp) },
            label = { Text("Home", fontSize = 11.sp, fontWeight = if (currentScreen == AppScreen.HOME) FontWeight.Bold else FontWeight.Normal) },
            modifier = Modifier.testTag("nav_home")
          )
          NavigationBarItem(
            selected = currentScreen == AppScreen.EXPERIMENTS,
            onClick = { viewModel.navigateTo(AppScreen.EXPERIMENTS) },
            icon = { Text("🧪", fontSize = 18.sp) },
            label = { Text("Experiments", fontSize = 11.sp, fontWeight = if (currentScreen == AppScreen.EXPERIMENTS) FontWeight.Bold else FontWeight.Normal) },
            modifier = Modifier.testTag("nav_experiments")
          )
          NavigationBarItem(
            selected = currentScreen == AppScreen.VIRTUAL_LAB,
            onClick = { viewModel.navigateTo(AppScreen.VIRTUAL_LAB) },
            icon = { Text("🔬", fontSize = 18.sp) },
            label = { Text("Workbench", fontSize = 11.sp, fontWeight = if (currentScreen == AppScreen.VIRTUAL_LAB) FontWeight.Bold else FontWeight.Normal) },
            modifier = Modifier.testTag("nav_workbench")
          )
          NavigationBarItem(
            selected = currentScreen == AppScreen.TITRATION_SIM,
            onClick = { viewModel.navigateTo(AppScreen.TITRATION_SIM) },
            icon = { Text("⚗️", fontSize = 18.sp) },
            label = { Text("Titration", fontSize = 11.sp, fontWeight = if (currentScreen == AppScreen.TITRATION_SIM) FontWeight.Bold else FontWeight.Normal) },
            modifier = Modifier.testTag("nav_titration")
          )
          NavigationBarItem(
            selected = currentScreen == AppScreen.PERIODIC_TABLE,
            onClick = { viewModel.navigateTo(AppScreen.PERIODIC_TABLE) },
            icon = { Text("📖", fontSize = 18.sp) },
            label = { Text("Elements", fontSize = 11.sp, fontWeight = if (currentScreen == AppScreen.PERIODIC_TABLE) FontWeight.Bold else FontWeight.Normal) },
            modifier = Modifier.testTag("nav_elements")
          )
        }
      }
    }
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      color = MaterialTheme.colorScheme.background
    ) {
      AnimatedContent(
        targetState = currentScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "screen_transition"
      ) { target ->
        when (target) {
          AppScreen.HOME -> HomeScreen(
            viewModel = viewModel,
            onNavigate = { screen -> viewModel.navigateTo(screen) }
          )
          AppScreen.EXPERIMENTS -> ExperimentLibraryScreen(
            viewModel = viewModel,
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.VIRTUAL_LAB -> VirtualLabScreen(
            viewModel = viewModel,
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.GUIDED_EXP -> GuidedExperimentScreen(
            viewModel = viewModel,
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.REACTION_SIM -> ReactionSimulatorScreen(
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.TITRATION_SIM -> TitrationSimulatorScreen(
            viewModel = viewModel,
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.CALCULATOR -> ChemistryCalculatorScreen(
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.PERIODIC_TABLE -> PeriodicTableScreen(
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.NOTES -> NotesScreen(
            onBack = { viewModel.navigateBack() },
            onNavigateToQuiz = { viewModel.navigateTo(AppScreen.QUIZ) }
          )
          AppScreen.QUIZ -> QuizScreen(
            viewModel = viewModel,
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.SAFETY -> SafetyScreen(
            onBack = { viewModel.navigateBack() }
          )
          AppScreen.NOTEBOOK -> LabNotebookScreen(
            viewModel = viewModel,
            onBack = { viewModel.navigateBack() }
          )
        }
      }
    }
  }
}
