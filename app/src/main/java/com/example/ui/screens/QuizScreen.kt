package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GamificationData
import com.example.model.QuestionType
import com.example.model.QuizQuestion
import com.example.util.RewardedAdStatus
import com.example.util.findActivity
import com.example.viewmodel.ChemLabViewModel

@Composable
fun QuizScreen(
  viewModel: ChemLabViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val adStatus by viewModel.adStatus.collectAsState()
  var adRewardClaimed by remember { mutableStateOf(false) }

  val questions: List<QuizQuestion> by viewModel.quizQuestions.collectAsState()
  val currentIndex by viewModel.currentQuizIndex.collectAsState()
  val selectedOption by viewModel.selectedOption.collectAsState()
  val isSubmitted by viewModel.isQuizSubmitted.collectAsState()
  val score by viewModel.quizScore.collectAsState()
  val isFinished by viewModel.quizFinished.collectAsState()
  val userProgress by viewModel.userProgress.collectAsState()

  val totalQuestions = questions.size
  val currentQuestion = questions.getOrNull(currentIndex) ?: questions[0]
  val progress = (currentIndex + 1).toFloat() / totalQuestions.toFloat()

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("quiz_screen")
  ) {
    // Header Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack, modifier = Modifier.testTag("quiz_back_button")) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Column {
          Text(
            text = "Chemistry Quiz & Mastery",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Earn +20 XP per correct question",
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      }

      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0x33F59E0B)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text("Score: $score/$totalQuestions", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B)))
        }
      }
    }

    if (!isFinished) {
      // Progress Bar
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(5.dp),
        color = MaterialTheme.colorScheme.primary,
        trackColor = Color(0x3364748B)
      )

      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Question Header Card
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ) {
                  Text(
                    text = when (currentQuestion.type) {
                      QuestionType.MULTIPLE_CHOICE -> "Multiple Choice"
                      QuestionType.TRUE_FALSE -> "True or False"
                      QuestionType.IDENTIFY_APPARATUS -> "Identify Apparatus"
                      QuestionType.IDENTIFY_REACTION -> "Identify Reaction"
                      QuestionType.BALANCE_EQUATION -> "Balance Equation"
                    },
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }

                Text(
                  text = "Question ${currentIndex + 1} of $totalQuestions",
                  style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
              }

              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = currentQuestion.question,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, lineHeight = 24.sp)
              )
            }
          }
        }

        // Answer Options
        items(currentQuestion.options.size) { optIndex ->
          val optionText = currentQuestion.options[optIndex]
          val isSelected = selectedOption == optIndex
          val isCorrect = optIndex == currentQuestion.correctIndex

          val containerColor = when {
            !isSubmitted -> if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
            isCorrect -> Color(0x3310B981)
            isSelected && !isCorrect -> Color(0x33EF4444)
            else -> MaterialTheme.colorScheme.surfaceVariant
          }

          val borderColor = when {
            !isSubmitted -> if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            isCorrect -> Color(0xFF10B981)
            isSelected && !isCorrect -> Color(0xFFEF4444)
            else -> Color.Transparent
          }

          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clickable(enabled = !isSubmitted) { viewModel.selectQuizOption(optIndex) }
              .testTag("quiz_option_$optIndex"),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(CircleShape)
                  .background(
                    if (isSubmitted && isCorrect) Color(0xFF10B981)
                    else if (isSubmitted && isSelected && !isCorrect) Color(0xFFEF4444)
                    else if (isSelected) MaterialTheme.colorScheme.primary
                    else Color(0x3364748B)
                  ),
                contentAlignment = Alignment.Center
              ) {
                if (isSubmitted && isCorrect) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                } else if (isSubmitted && isSelected && !isCorrect) {
                  Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                } else {
                  Text(
                    text = ('A' + optIndex).toString(),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White)
                  )
                }
              }

              Spacer(modifier = Modifier.width(12.dp))
              Text(
                text = optionText,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = if (isSelected || (isSubmitted && isCorrect)) FontWeight.Bold else FontWeight.Normal
                ),
                modifier = Modifier.weight(1f)
              )
            }
          }
        }

        // Post-Answer Feedback & Scientific Explanation
        item {
          AnimatedVisibility(visible = isSubmitted) {
            val userIsCorrect = selectedOption == currentQuestion.correctIndex
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (userIsCorrect) Color(0xFF0F2F24) else Color(0xFF2D1217)
              )
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(if (userIsCorrect) "✅ Correct! (+20 XP)" else "❌ Incorrect", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = if (userIsCorrect) Color(0xFF34D399) else Color(0xFFF87171)))
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = currentQuestion.explanation,
                  style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFE2E8F0), lineHeight = 18.sp)
                )
              }
            }
          }
        }

        // Submit / Next Question Button
        item {
          Spacer(modifier = Modifier.height(10.dp))
          if (!isSubmitted) {
            Button(
              onClick = { viewModel.submitQuizAnswer() },
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("submit_quiz_answer_button"),
              shape = RoundedCornerShape(12.dp),
              enabled = selectedOption != null,
              colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
              Text("Submit Answer", fontWeight = FontWeight.Bold)
            }
          } else {
            Button(
              onClick = { viewModel.nextQuizQuestion() },
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("next_quiz_question_button"),
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) {
              Text(
                if (currentIndex < totalQuestions - 1) "Next Question" else "View Quiz Summary",
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    } else {
      // Quiz Finished Screen
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text("🏆", fontSize = 64.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = "Quiz Completed!",
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        val accuracy = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()
        Text(
          text = "You scored $score out of $totalQuestions ($accuracy% Accuracy)",
          style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "⭐ +${score * 20 + 50} Total XP Earned!",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Current Chemistry Level: ${GamificationData.getChemistRank(userProgress.xp)}",
              style = MaterialTheme.typography.bodySmall
            )

            if (!adRewardClaimed) {
              Spacer(modifier = Modifier.height(14.dp))
              Button(
                onClick = {
                  val activity = context.findActivity()
                  if (activity != null) {
                    if (adStatus == RewardedAdStatus.FAILED_TO_LOAD) {
                      viewModel.reloadRewardedAd()
                    } else {
                      viewModel.showRewardedAd(
                        activity = activity,
                        onRewardEarnedCallback = {
                          adRewardClaimed = true
                        }
                      )
                    }
                  }
                },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(40.dp)
                  .testTag("quiz_rewarded_ad_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = if (adStatus == RewardedAdStatus.LOADED) Color(0xFFF59E0B) else Color(0xFF334155)
                ),
                enabled = adStatus != RewardedAdStatus.LOADING
              ) {
                Text(
                  text = when (adStatus) {
                    RewardedAdStatus.LOADING -> "Loading Bonus Ad..."
                    RewardedAdStatus.LOADED -> "🎬 Watch Ad for +50 Bonus XP"
                    RewardedAdStatus.FAILED_TO_LOAD -> "Ad Unavailable (Tap to Retry ↻)"
                    else -> "🎬 Watch Ad for +50 Bonus XP"
                  },
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = Color.White
                )
              }
            } else {
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = "🎉 Bonus +50 XP Claimed!",
                style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Button(
            onClick = { viewModel.restartQuiz() },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Retry Quiz")
            }
          }

          Button(
            onClick = onBack,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
          ) {
            Text("Back to Home")
          }
        }
      }
    }
  }
}
