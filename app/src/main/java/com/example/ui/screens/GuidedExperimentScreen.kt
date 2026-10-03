package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.LabGlasswareSim
import com.example.viewmodel.ChemLabViewModel

@Composable
fun GuidedExperimentScreen(
  viewModel: ChemLabViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val experiment by viewModel.selectedExperiment.collectAsState()
  val stepIndex by viewModel.guidedStepIndex.collectAsState()
  val isActionDone by viewModel.isStepActionDone.collectAsState()
  val isVoiceEnabled by viewModel.isVoiceEnabled.collectAsState()

  val totalSteps = experiment.steps.size
  val currentStep = experiment.steps.getOrNull(stepIndex) ?: experiment.steps[0]
  val progress = (stepIndex + 1).toFloat() / totalSteps.toFloat()

  var isWhyExpanded by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .testTag("guided_experiment_screen")
  ) {
    // Header Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        IconButton(onClick = onBack, modifier = Modifier.testTag("guided_exp_back_button")) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Column {
          Text(
            text = experiment.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 1
          )
          Text(
            text = "Step ${stepIndex + 1} of $totalSteps",
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      }

      // Voice Narration Toggle
      IconButton(
        onClick = { viewModel.toggleVoice() },
        modifier = Modifier.testTag("toggle_voice_narrator_button")
      ) {
        Icon(
          if (isVoiceEnabled) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeOff,
          contentDescription = "Voice Guidance",
          tint = if (isVoiceEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Step Progress Bar
    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp),
      color = MaterialTheme.colorScheme.primary,
      trackColor = Color(0x3364748B)
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Interactive Animated Simulation Card for this step
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "Apparatus Simulation: ${currentStep.title}",
          style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF090D16)),
          contentAlignment = Alignment.Center
        ) {
          val liquidLevel = if (isActionDone) 0.65f else 0.35f
          val liquidColor = Color(currentStep.colorShiftHex)

          LabGlasswareSim(
            apparatusType = if (experiment.id == "exp_4") "test_tube" else "conical_flask",
            liquidColor = liquidColor,
            liquidLevel = liquidLevel,
            isHeating = currentStep.animationType == "HEAT",
            hasBubbles = isActionDone || currentStep.animationType == "HEAT",
            hasPrecipitate = isActionDone && currentStep.animationType in listOf("OBSERVE", "MEASURE"),
            precipitateColor = Color(0xFFFACC15),
            modifier = Modifier.fillMaxSize()
          )

          // Step Action Pill
          if (!isActionDone) {
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = Color(0xCC0284C7),
              modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
                .clickable { viewModel.performGuidedStepAction() }
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
              ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = currentStep.actionLabel,
                  style = MaterialTheme.typography.labelMedium.copy(color = Color.White, fontWeight = FontWeight.Bold)
                )
              }
            }
          } else {
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = Color(0xCC10B981),
              modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
              ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Step Action Performed!",
                  style = MaterialTheme.typography.labelMedium.copy(color = Color.White, fontWeight = FontWeight.Bold)
                )
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Step Instruction Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Step ${currentStep.stepNumber}: ${currentStep.title}",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = currentStep.instruction,
          style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp)
        )

        if (currentStep.hint.isNotBlank()) {
          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.Top
            ) {
              Text("💡", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Tip: ${currentStep.hint}",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // "Why?" Explanation Expandable Box
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { isWhyExpanded = !isWhyExpanded },
          shape = RoundedCornerShape(10.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.HelpOutline, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Why do we perform this step?",
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                  )
                )
              }
              Text(
                text = if (isWhyExpanded) "Hide ▲" else "Read Why ▼",
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary)
              )
            }

            AnimatedVisibility(visible = isWhyExpanded) {
              Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = currentStep.whyExplanation,
                  style = MaterialTheme.typography.bodySmall.copy(lineHeight = 20.sp)
                )
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Navigation Buttons (Previous, Next, Finish)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      if (stepIndex > 0) {
        OutlinedButton(
          onClick = { viewModel.prevGuidedStep() },
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("guided_prev_button"),
          shape = RoundedCornerShape(12.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Previous")
          }
        }
      }

      val isLast = stepIndex == totalSteps - 1
      Button(
        onClick = {
          if (!isActionDone) {
            viewModel.performGuidedStepAction()
          } else {
            if (isLast) {
              viewModel.nextGuidedStep()
              onBack()
            } else {
              viewModel.nextGuidedStep()
            }
          }
        },
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("guided_next_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isLast) Color(0xFF10B981) else MaterialTheme.colorScheme.primary
        )
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = if (!isActionDone) "Perform Step Action" else if (isLast) "Finish Experiment (+50 XP)" else "Next Step",
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.width(6.dp))
          Icon(
            if (isLast) Icons.Default.Star else Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}
