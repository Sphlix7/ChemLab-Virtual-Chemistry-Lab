package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.data.ChemicalData
import com.example.data.ReactionData
import com.example.model.Chemical
import com.example.model.ChemicalCategory
import com.example.model.ChemicalReaction
import com.example.model.ReactionType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReactionSimulatorScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val selectedReactants = remember { mutableStateListOf<Chemical>() }
  var evaluatedReaction by remember { mutableStateOf<ChemicalReaction?>(null) }
  var hasSimulated by remember { mutableStateOf(false) }
  var filterCategory by remember { mutableStateOf<ChemicalCategory?>(null) }
  var filterReactionType by remember { mutableStateOf<ReactionType?>(null) }

  val availableChemicals = remember(filterCategory) {
    if (filterCategory == null) ChemicalData.chemicals
    else ChemicalData.chemicals.filter { it.category == filterCategory }
  }

  fun simulate() {
    hasSimulated = true
    val ids = selectedReactants.map { it.id }
    evaluatedReaction = ReactionData.findReaction(ids)
  }

  fun reset() {
    selectedReactants.clear()
    evaluatedReaction = null
    hasSimulated = false
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("reaction_simulator_screen"),
    contentPadding = PaddingValues(bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Top Bar
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack, modifier = Modifier.testTag("reaction_sim_back_button")) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Column {
          Text(
            text = "Chemical Reaction Simulator",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Predict and observe multi-chemical interactions",
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      }
    }

    // Safety Simulation Disclaimer Banner
    item {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1E293B)
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "Safety Mode: Demonstrates reaction stoichiometry & energetics educationally. Never mix unknown real-world chemicals.",
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFE2E8F0), fontSize = 12.sp)
          )
        }
      }
    }

    // Selected Reactants Chamber
    item {
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
              text = "Reaction Chamber (${selectedReactants.size} Selected)",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            if (selectedReactants.isNotEmpty()) {
              Text(
                text = "Clear All",
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold),
                modifier = Modifier.clickable { reset() }
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          if (selectedReactants.isEmpty()) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "Select 2 or more reactants from the list below",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
          } else {
            FlowRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              selectedReactants.forEach { chem ->
                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = MaterialTheme.colorScheme.surface
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                  ) {
                    Box(
                      modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color(chem.colorHex))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "${chem.formula} (${chem.name})",
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                      Icons.Default.Close,
                      contentDescription = "Remove",
                      modifier = Modifier
                        .size(14.dp)
                        .clickable { selectedReactants.remove(chem); hasSimulated = false },
                      tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Button(
              onClick = { simulate() },
              modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("simulate_reaction_button"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
              enabled = selectedReactants.size >= 2
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Simulate Chemical Reaction", fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }

    // Reaction Result Card
    item {
      AnimatedVisibility(visible = hasSimulated) {
        val reaction = evaluatedReaction
        if (reaction != null) {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = Color(0x3310B981)
                ) {
                  Text(
                    text = "Reaction Type: ${reaction.reactionType.label}",
                    style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF10B981), fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                  )
                }
                Text("✅ Reaction Occurs!", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold))
              }

              Spacer(modifier = Modifier.height(12.dp))

              // Balanced Equation
              Text(
                text = "Balanced Equation:",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
              )
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF1E293B),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
              ) {
                Text(
                  text = reaction.equation,
                  style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF38BDF8),
                    fontWeight = FontWeight.Bold
                  ),
                  modifier = Modifier.padding(12.dp)
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              // Observable Changes
              Text(
                text = "Observable Changes:",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "• Color / Visual: ${reaction.colorChange}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
              )
              Text(
                text = "• Temperature Shift: ${reaction.tempChange}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
              )
              if (reaction.gasProduced != null) {
                Text(
                  text = "• Gas Formed: ${reaction.gasProduced}",
                  style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF38BDF8))
                )
              }
              if (reaction.precipitateColor != null) {
                Text(
                  text = "• Insoluble Precipitate: ${reaction.precipitateColor}",
                  style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFFACC15))
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              // Scientific Explanation
              Text(
                text = "Scientific Mechanism:",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = reaction.explanation,
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFCBD5E1), lineHeight = 18.sp)
              )

              if (reaction.safetyWarning.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                  shape = RoundedCornerShape(8.dp),
                  color = Color(0x33EF4444)
                ) {
                  Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFF87171), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = reaction.safetyWarning,
                      style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFF87171))
                    )
                  }
                }
              }
            }
          }
        } else {
          // No reaction
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(
                text = "No Observable Reaction",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "These selected reagents do not react under standard laboratory conditions without a specific external catalyst or high temperature.",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
          }
        }
      }
    }

    // Curated Reaction Quick Presets
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Try Popular Reactions (Auto-Select):",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          val presets = listOf(
            "HCl + NaOH (Neutralization)" to listOf("hcl", "naoh"),
            "Pb(NO3)2 + KI (Golden Rain)" to listOf("pbno32", "ki"),
            "CuSO4 + Zn (Displacement)" to listOf("cuso4", "zn"),
            "HCl + CaCO3 (CO2 Fizz)" to listOf("hcl", "caco3"),
            "HCl + Mg (H2 Pop)" to listOf("hcl", "mg"),
            "BaCl2 + H2SO4 (White Ppt)" to listOf("bacl2", "h2so4"),
            "Acetic Acid + Ethanol (Ester)" to listOf("ch3cooh", "ethanol")
          )

          items(presets) { (label, ids) ->
            ElevatedFilterChip(
              selected = false,
              onClick = {
                selectedReactants.clear()
                val chems = ids.mapNotNull { id -> ChemicalData.chemicals.find { it.id == id } }
                selectedReactants.addAll(chems)
                simulate()
              },
              label = { Text(label, fontSize = 12.sp) }
            )
          }
        }
      }
    }

    // Chemical Shelf
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Select Reagents to Add:",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          item {
            ElevatedFilterChip(
              selected = filterCategory == null,
              onClick = { filterCategory = null },
              label = { Text("All", fontSize = 12.sp) }
            )
          }
          items(ChemicalCategory.values()) { cat ->
            ElevatedFilterChip(
              selected = filterCategory == cat,
              onClick = { filterCategory = if (filterCategory == cat) null else cat },
              label = { Text(cat.name.lowercase().replaceFirstChar { it.uppercase() }, fontSize = 12.sp) }
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          maxItemsInEachRow = 2
        ) {
          availableChemicals.forEach { chem ->
            val isSelected = selectedReactants.any { it.id == chem.id }
            Card(
              modifier = Modifier
                .weight(1f)
                .clickable {
                  if (isSelected) {
                    selectedReactants.removeIf { it.id == chem.id }
                  } else {
                    selectedReactants.add(chem)
                  }
                  hasSimulated = false
                }
                .testTag("toggle_reactant_${chem.id}"),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
              )
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(chem.colorHex)),
                  contentAlignment = Alignment.Center
                ) {
                  if (isSelected) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                  }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = chem.formula,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                  )
                  Text(
                    text = chem.name,
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    maxLines = 1
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
