package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ExperimentData
import com.example.data.GamificationData
import com.example.model.Experiment
import com.example.util.RewardedAdStatus
import com.example.util.findActivity
import com.example.viewmodel.AppScreen
import com.example.viewmodel.ChemLabViewModel

data class QuickActionItem(
  val title: String,
  val icon: ImageVector,
  val emoji: String,
  val color: Color,
  val screen: AppScreen
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
  viewModel: ChemLabViewModel,
  onNavigate: (AppScreen) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val userProgress by viewModel.userProgress.collectAsState()
  val adStatus by viewModel.adStatus.collectAsState()
  var rewardNotification by remember { mutableStateOf<String?>(null) }
  var searchQuery by remember { mutableStateOf("") }
  var factIndex by remember { mutableIntStateOf(0) }
  val currentFact = GamificationData.dailyFacts[factIndex % GamificationData.dailyFacts.size]

  val quickActions = listOf(
    QuickActionItem("Experiments", Icons.Default.Science, "🧪", Color(0xFF0284C7), AppScreen.EXPERIMENTS),
    QuickActionItem("Virtual Lab", Icons.Default.Thermostat, "🔬", Color(0xFF0D9488), AppScreen.VIRTUAL_LAB),
    QuickActionItem("Reactions", Icons.Default.AutoAwesome, "⚗️", Color(0xFF8B5CF6), AppScreen.REACTION_SIM),
    QuickActionItem("Titration Lab", Icons.Default.WaterDrop, "💧", Color(0xFFEC4899), AppScreen.TITRATION_SIM),
    QuickActionItem("Calculator", Icons.Default.Calculate, "🧮", Color(0xFFF59E0B), AppScreen.CALCULATOR),
    QuickActionItem("Periodic Table", Icons.Default.TableChart, "📖", Color(0xFF10B981), AppScreen.PERIODIC_TABLE),
    QuickActionItem("Chemistry Notes", Icons.Default.MenuBook, "📚", Color(0xFF6366F1), AppScreen.NOTES),
    QuickActionItem("Quiz", Icons.Default.Psychology, "🧠", Color(0xFFF97316), AppScreen.QUIZ),
    QuickActionItem("Lab Safety", Icons.Default.Shield, "🛡️", Color(0xFFEF4444), AppScreen.SAFETY),
    QuickActionItem("Lab Notebook", Icons.Default.Book, "📓", Color(0xFF14B8A6), AppScreen.NOTEBOOK)
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("home_screen_column"),
    contentPadding = PaddingValues(bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {
    // Header & App Branding
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(Color(0xFF0B192C), Color(0xFF1E293B))
            )
          )
          .padding(top = 16.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
      ) {
        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(46.dp)
                  .clip(CircleShape)
                  .background(
                    Brush.radialGradient(
                      listOf(Color(0xFF38BDF8), Color(0xFF0284C7))
                    )
                  ),
                contentAlignment = Alignment.Center
              ) {
                Text(text = "🧪", fontSize = 24.sp)
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "ChemLab",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                )
                Text(
                  text = "Virtual Chemistry Lab",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF94A3B8)
                  )
                )
              }
            }

            // Streak & XP pill
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = Color(0x3338BDF8),
              modifier = Modifier.clip(RoundedCornerShape(20.dp))
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              ) {
                Text("🔥", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "${userProgress.streakDays}d Streak",
                  style = MaterialTheme.typography.labelMedium.copy(
                    color = Color(0xFFF59E0B),
                    fontWeight = FontWeight.Bold
                  )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "⭐ ${userProgress.xp} XP",
                  style = MaterialTheme.typography.labelMedium.copy(
                    color = Color(0xFF38BDF8),
                    fontWeight = FontWeight.Bold
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Learn Chemistry by Doing Experiments!",
            style = MaterialTheme.typography.headlineSmall.copy(
              color = Color.White,
              fontWeight = FontWeight.SemiBold
            )
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Search Bar
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search experiments, chemicals or topics...", color = Color(0xFF94A3B8), fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF38BDF8)) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("home_search_bar"),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = Color(0xFF0F172A),
              unfocusedContainerColor = Color(0xFF0F172A),
              focusedBorderColor = Color(0xFF38BDF8),
              unfocusedBorderColor = Color(0xFF334155),
              focusedTextColor = Color.White,
              unfocusedTextColor = Color.White
            ),
            singleLine = true
          )
        }
      }
    }

    // Hero Banner Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
          .clickable { onNavigate(AppScreen.VIRTUAL_LAB) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Column {
          Image(
            painter = painterResource(id = R.drawable.chemlab_hero_banner),
            contentDescription = "Virtual Chemistry Laboratory Workbench",
            modifier = Modifier
              .fillMaxWidth()
              .height(130.dp),
            contentScale = ContentScale.Crop
          )
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Interactive Virtual Workbench",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Mix reagents, control Bunsen burner, observe precipitates & gases safely!",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
            Button(
              onClick = { onNavigate(AppScreen.VIRTUAL_LAB) },
              modifier = Modifier.testTag("enter_virtual_lab_button"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) {
              Text("Enter Lab")
            }
          }
        }
      }
    }

    // Student Progress & Mastery Card
    item {
      val rank = GamificationData.getChemistRank(userProgress.xp)
      val (nextRank, targetXp) = GamificationData.getNextRankThreshold(userProgress.xp)
      val progressRatio = (userProgress.xp.toFloat() / targetXp.toFloat()).coerceIn(0f, 1f)

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
            Column {
              Text(
                text = "Student Academic Mastery",
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
              )
              Text(
                text = rank,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
            }
            Text(
              text = "${(progressRatio * 100).toInt()}% to $nextRank",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          LinearProgressIndicator(
            progress = { progressRatio },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF06B6D4),
            trackColor = Color(0x3364748B)
          )

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Earn XP by completing experiments, titrations, and scoring in quizzes!",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Voluntary Rewarded Test Ad Action
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0x1AF59E0B),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "🎬 Watch Ad for +50 Bonus XP",
                  style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                )
                Text(
                  text = when (adStatus) {
                    RewardedAdStatus.LOADING -> "Loading rewarded ad..."
                    RewardedAdStatus.LOADED -> "Ad ready! Watch to earn reward."
                    RewardedAdStatus.SHOWN -> "Showing rewarded ad..."
                    RewardedAdStatus.REWARD_EARNED -> "Reward earned! +50 XP"
                    RewardedAdStatus.FAILED_TO_LOAD -> "Ad unavailable offline. Tap to retry."
                    else -> "Tap to watch and boost your level"
                  },
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
              }

              Spacer(modifier = Modifier.width(8.dp))

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
                          rewardNotification = "🎉 +50 Bonus XP Earned!"
                        }
                      )
                    }
                  }
                },
                modifier = Modifier
                  .height(36.dp)
                  .testTag("watch_rewarded_ad_button"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = if (adStatus == RewardedAdStatus.LOADED) Color(0xFFF59E0B) else Color(0xFF334155)
                ),
                enabled = adStatus != RewardedAdStatus.LOADING
              ) {
                Text(
                  text = when (adStatus) {
                    RewardedAdStatus.LOADING -> "Loading..."
                    RewardedAdStatus.LOADED -> "Watch Ad"
                    RewardedAdStatus.FAILED_TO_LOAD -> "Retry ↻"
                    else -> "Watch"
                  },
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              }
            }
          }

          AnimatedVisibility(visible = rewardNotification != null) {
            Text(
              text = rewardNotification ?: "",
              style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF10B981), fontWeight = FontWeight.Bold),
              modifier = Modifier.padding(top = 6.dp)
            )
          }
        }
      }
    }

    // Quick-Access Buttons
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Quick Lab Tools",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          modifier = Modifier.padding(bottom = 12.dp)
        )

        FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
          maxItemsInEachRow = 2
        ) {
          quickActions.forEach { action ->
            Card(
              modifier = Modifier
                .weight(1f)
                .clickable { onNavigate(action.screen) }
                .testTag("quick_action_${action.title.lowercase().replace(" ", "_")}"),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(action.color.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(text = action.emoji, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = action.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
              }
            }
          }
        }
      }
    }

    // Daily Chemistry Fact
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
          .clickable { factIndex++ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.Top
        ) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(Color(0xFF334155)),
            contentAlignment = Alignment.Center
          ) {
            Text(currentFact.icon, fontSize = 20.sp)
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Daily Chemistry Fact",
                style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Tap for next ↻",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
              )
            }
            Text(
              text = currentFact.title,
              style = MaterialTheme.typography.titleSmall.copy(color = Color.White, fontWeight = FontWeight.Bold),
              modifier = Modifier.padding(vertical = 2.dp)
            )
            Text(
              text = currentFact.fact,
              style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFCBD5E1), lineHeight = 18.sp)
            )
          }
        }
      }
    }

    // Popular Experiments Section
    item {
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Popular Experiments",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "See All",
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold),
            modifier = Modifier.clickable { onNavigate(AppScreen.EXPERIMENTS) }
          )
        }

        LazyRow(
          contentPadding = PaddingValues(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          val popular = ExperimentData.experiments.take(4)
          items(popular) { exp ->
            ExperimentCardItem(
              experiment = exp,
              onStart = {
                viewModel.startGuidedExperiment(exp)
              }
            )
          }
        }
      }
    }

    // Continue Learning & Recently Viewed
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Continue Learning",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          modifier = Modifier.padding(bottom = 10.dp)
        )

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigate(AppScreen.TITRATION_SIM) },
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0284C7).copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Text("⚗️", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Titration Lab & pH Graph",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Acid-Base volumetric analysis with stoichiometric curve",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigate(AppScreen.QUIZ) },
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF10B981).copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Text("🧠", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Daily Chemistry Challenge",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "Test yourself on formulas, equations & apparatus",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
          }
        }
      }
    }
  }
}

@Composable
fun ExperimentCardItem(
  experiment: Experiment,
  onStart: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.width(260.dp),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = Color(0x2238BDF8)
        ) {
          Text(
            text = experiment.category.displayName,
            style = MaterialTheme.typography.labelSmall.copy(
              color = Color(0xFF0284C7),
              fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
        Text(
          text = "⏱ ${experiment.timeMinutes}m",
          style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = experiment.title,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = experiment.description,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(12.dp))
      Button(
        onClick = onStart,
        modifier = Modifier
          .fillMaxWidth()
          .height(38.dp)
          .testTag("start_exp_${experiment.id}"),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Start Experiment", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
