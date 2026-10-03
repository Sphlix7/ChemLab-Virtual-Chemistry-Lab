package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.data.ChemicalData
import com.example.model.ApparatusItem
import com.example.model.Chemical
import com.example.model.ChemicalCategory
import com.example.ui.components.LabGlasswareSim
import com.example.viewmodel.ChemLabViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VirtualLabScreen(
  viewModel: ChemLabViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val selectedApparatus by viewModel.labApparatus.collectAsState()
  val chemicalsInVessel by viewModel.labChemicalsInVessel.collectAsState()
  val liquidColor by viewModel.labLiquidColor.collectAsState()
  val liquidLevel by viewModel.labLiquidLevel.collectAsState()
  val currentTemp by viewModel.labTemp.collectAsState()
  val currentPh by viewModel.labPh.collectAsState()
  val isHeating by viewModel.isLabHeating.collectAsState()
  val hasBubbles by viewModel.hasLabBubbles.collectAsState()
  val hasPrecipitate by viewModel.hasLabPrecipitate.collectAsState()
  val precipitateColor by viewModel.labPrecipitateColor.collectAsState()
  val currentReaction by viewModel.labCurrentReaction.collectAsState()
  val statusMessage by viewModel.labStatusMessage.collectAsState()

  var showLogDialog by remember { mutableStateOf(false) }
  var userObservationNote by remember { mutableStateOf("") }
  var selectedCategoryFilter by remember { mutableStateOf<ChemicalCategory?>(null) }

  // Filtered chemical shelf
  val shelfChemicals = remember(selectedCategoryFilter) {
    if (selectedCategoryFilter == null) ChemicalData.chemicals
    else ChemicalData.chemicals.filter { it.category == selectedCategoryFilter }
  }

  // Calculate approximate liquid volume in vessel
  val currentVolumeMl = (liquidLevel * selectedApparatus.capacityMl).toInt()
  val currentMassGrams = currentVolumeMl * 1.02 // estimated solution density

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("virtual_lab_screen"),
    contentPadding = PaddingValues(bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header Bar
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(onClick = onBack, modifier = Modifier.testTag("virtual_lab_back_button")) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
          Column {
            Text(
              text = "Virtual Chemistry Workbench",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Interactive Glassware & Reagent Simulation",
              style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }

        IconButton(
          onClick = { viewModel.resetLabWorkbench() },
          modifier = Modifier.testTag("reset_lab_workbench_button")
        ) {
          Icon(Icons.Default.CleaningServices, contentDescription = "Wash & Reset Glassware", tint = MaterialTheme.colorScheme.primary)
        }
      }
    }

    // Apparatus Selector Bar
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Select Laboratory Glassware:",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          val selectable = ChemicalData.apparatusList.filter {
            it.id in listOf("beaker", "conical_flask", "test_tube")
          }
          items(selectable) { item ->
            ElevatedFilterChip(
              selected = selectedApparatus.id == item.id,
              onClick = { viewModel.setLabApparatus(item) },
              label = { Text("${item.symbol} ${item.name}") },
              colors = FilterChipDefaults.elevatedFilterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
              )
            )
          }
        }
      }
    }

    // Main Simulation Stage
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Glassware info row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFF1E293B)
            ) {
              Text(
                text = "${selectedApparatus.name} (${selectedApparatus.capacityMl} mL)",
                style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }

            if (isHeating) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0x33F97316)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color(0xFFF97316), modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Burner Heating ON", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFF97316), fontWeight = FontWeight.Bold))
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Animated Apparatus Viewport
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(230.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFF090D16)),
            contentAlignment = Alignment.Center
          ) {
            LabGlasswareSim(
              apparatusType = selectedApparatus.id,
              liquidColor = liquidColor,
              liquidLevel = liquidLevel,
              isHeating = isHeating,
              hasBubbles = hasBubbles,
              hasPrecipitate = hasPrecipitate,
              precipitateColor = precipitateColor,
              modifier = Modifier.fillMaxSize()
            )

            // Real-time Sensor Overlays
            Column(
              modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              // Digital Thermometer
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xDD1E293B)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Icon(Icons.Default.Thermostat, contentDescription = null, tint = Color(0xFFF43F5E), modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "${"%.1f".format(currentTemp)}°C",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold)
                  )
                }
              }

              // Digital pH Meter
              val phColor = when {
                currentPh < 6.5 -> Color(0xFFEF4444)
                currentPh > 7.5 -> Color(0xFF3B82F6)
                else -> Color(0xFF10B981)
              }
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xDD1E293B)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text("pH", style = MaterialTheme.typography.labelSmall.copy(color = phColor, fontWeight = FontWeight.Bold))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "%.1f".format(currentPh),
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold)
                  )
                }
              }

              // Volume & Mass
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xDD1E293B)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = "$currentVolumeMl mL / ${"%.1f".format(currentMassGrams)}g",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Apparatus Controls: Bunsen Burner & Log Notebook
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { viewModel.toggleLabHeating() },
              modifier = Modifier
                .weight(1f)
                .testTag("toggle_heat_button"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isHeating) Color(0xFFEA580C) else Color(0xFF334155)
              )
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isHeating) "Extinguish Flame" else "Ignite Bunsen Burner", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }

            OutlinedButton(
              onClick = { showLogDialog = true },
              modifier = Modifier
                .weight(1f)
                .testTag("log_observation_button"),
              shape = RoundedCornerShape(10.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Book, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Log Observation", fontSize = 12.sp)
              }
            }
          }
        }
      }
    }

    // Reaction Feedback / Status Card
    item {
      AnimatedVisibility(visible = currentReaction != null) {
        val rxn = currentReaction
        if (rxn != null) {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = Color(0x3310B981)
                ) {
                  Text(
                    text = "Reaction Detected: ${rxn.reactionType.label}",
                    style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF10B981), fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
                Text("⭐ +25 XP", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFF59E0B), fontWeight = FontWeight.Bold))
              }

              Spacer(modifier = Modifier.height(8.dp))
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = rxn.equation,
                  style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                  ),
                  modifier = Modifier.padding(12.dp)
                )
              }

              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = rxn.explanation,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )

              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "Observation: ${rxn.colorChange}. ${rxn.tempChange}.",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
              )

              if (rxn.safetyWarning.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "⚠️ Safety Note: ${rxn.safetyWarning}",
                  style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFF97316))
                )
              }
            }
          }
        }
      }
    }

    // Chemicals Currently in Vessel
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Chemicals in Vessel (${chemicalsInVessel.size}):",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          if (chemicalsInVessel.isNotEmpty()) {
            Text(
              text = "Clear Vessel",
              style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold),
              modifier = Modifier.clickable { viewModel.resetLabWorkbench() }
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (chemicalsInVessel.isEmpty()) {
          Text(
            text = "Vessel is empty. Tap any chemical below to add it.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        } else {
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            chemicalsInVessel.forEach { chem ->
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
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
                    text = "${chem.name} (${chem.formula})",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
                  )
                }
              }
            }
          }
        }
      }
    }

    // Chemical Reagents Shelf
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Reagent Shelf (Tap to Add):",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category filter chips for shelf
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          item {
            ElevatedFilterChip(
              selected = selectedCategoryFilter == null,
              onClick = { selectedCategoryFilter = null },
              label = { Text("All", fontSize = 12.sp) }
            )
          }
          items(ChemicalCategory.values()) { cat ->
            ElevatedFilterChip(
              selected = selectedCategoryFilter == cat,
              onClick = { selectedCategoryFilter = if (selectedCategoryFilter == cat) null else cat },
              label = { Text(cat.name.lowercase().replaceFirstChar { it.uppercase() }, fontSize = 12.sp) }
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Chemical buttons grid
        FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          maxItemsInEachRow = 2
        ) {
          shelfChemicals.forEach { chem ->
            Card(
              modifier = Modifier
                .weight(1f)
                .clickable { viewModel.addChemicalToLab(chem) }
                .testTag("add_chem_${chem.id}"),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(chem.colorHex))
                    .border(1.dp, Color(0x33FFFFFF), CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
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

  // Dialog for logging observation to notebook
  if (showLogDialog) {
    AlertDialog(
      onDismissRequest = { showLogDialog = false },
      title = { Text("Log to Lab Notebook") },
      text = {
        Column {
          Text(
            text = "Record your virtual workbench findings into your persistent student notebook:",
            style = MaterialTheme.typography.bodySmall
          )
          Spacer(modifier = Modifier.height(10.dp))
          OutlinedTextField(
            value = userObservationNote,
            onValueChange = { userObservationNote = it },
            placeholder = { Text("Write personal observations, questions, or conclusions...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.logCurrentObservationToNotebook(userObservationNote)
            userObservationNote = ""
            showLogDialog = false
          },
          modifier = Modifier.testTag("confirm_log_observation")
        ) {
          Text("Save Entry (+30 XP)")
        }
      },
      dismissButton = {
        TextButton(onClick = { showLogDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }
}
