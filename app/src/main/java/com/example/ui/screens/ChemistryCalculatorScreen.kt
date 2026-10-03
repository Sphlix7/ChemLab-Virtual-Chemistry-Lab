package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.util.CalculationResult
import com.example.util.ChemistryCalculatorHelper

enum class CalcTab(val label: String, val icon: String) {
  MOLAR_MASS("Molar Mass", "⚖️"),
  MOLES("Moles & Mass", "🔢"),
  MOLARITY("Molarity", "🧪"),
  NORMALITY("Normality", "⚗️"),
  DILUTION("Dilution", "💧"),
  PH("pH & pOH", "📊"),
  GAS_LAW("Gas Laws", "🎈"),
  DENSITY("Density", "📐")
}

@Composable
fun ChemistryCalculatorScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(CalcTab.MOLAR_MASS) }

  // Input states
  var formulaInput by remember { mutableStateOf("H2SO4") }
  var massInput by remember { mutableStateOf("49.04") }
  var molarMassInput by remember { mutableStateOf("98.08") }
  var molesInput by remember { mutableStateOf("0.5") }
  var volumeInput by remember { mutableStateOf("1.0") }
  var valencyInput by remember { mutableStateOf("2") }
  var m1Input by remember { mutableStateOf("2.0") }
  var v1Input by remember { mutableStateOf("50.0") }
  var v2Input by remember { mutableStateOf("250.0") }
  var hConcInput by remember { mutableStateOf("0.001") }
  var pressureInput by remember { mutableStateOf("1.0") }
  var gasVolumeInput by remember { mutableStateOf("22.4") }
  var tempKelvinInput by remember { mutableStateOf("273.15") }
  var densityMassInput by remember { mutableStateOf("150.0") }
  var densityVolInput by remember { mutableStateOf("50.0") }

  var currentResult by remember {
    mutableStateOf(ChemistryCalculatorHelper.calculateMolarMass("H2SO4"))
  }

  fun compute() {
    currentResult = when (selectedTab) {
      CalcTab.MOLAR_MASS -> ChemistryCalculatorHelper.calculateMolarMass(formulaInput)
      CalcTab.MOLES -> ChemistryCalculatorHelper.calculateMolesAndMass(
        massInput.toDoubleOrNull() ?: 0.0,
        molarMassInput.toDoubleOrNull() ?: 1.0
      )
      CalcTab.MOLARITY -> ChemistryCalculatorHelper.calculateMolarity(
        molesInput.toDoubleOrNull() ?: 0.0,
        volumeInput.toDoubleOrNull() ?: 1.0
      )
      CalcTab.NORMALITY -> ChemistryCalculatorHelper.calculateNormality(
        molesInput.toDoubleOrNull() ?: 1.0,
        valencyInput.toDoubleOrNull() ?: 1.0
      )
      CalcTab.DILUTION -> ChemistryCalculatorHelper.calculateDilution(
        m1Input.toDoubleOrNull() ?: 1.0,
        v1Input.toDoubleOrNull() ?: 1.0,
        null,
        v2Input.toDoubleOrNull() ?: 1.0
      )
      CalcTab.PH -> ChemistryCalculatorHelper.calculatePhAndPoh(
        hConcInput.toDoubleOrNull() ?: 0.001
      )
      CalcTab.GAS_LAW -> ChemistryCalculatorHelper.calculateGasLaw(
        pressureInput.toDoubleOrNull() ?: 1.0,
        gasVolumeInput.toDoubleOrNull() ?: 22.4,
        tempKelvinInput.toDoubleOrNull() ?: 273.15
      )
      CalcTab.DENSITY -> ChemistryCalculatorHelper.calculateDensity(
        densityMassInput.toDoubleOrNull() ?: 0.0,
        densityVolInput.toDoubleOrNull() ?: 1.0
      )
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("chemistry_calculator_screen"),
    contentPadding = PaddingValues(bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header Bar
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack, modifier = Modifier.testTag("calculator_back_button")) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Column {
          Text(
            text = "Chemical Calculator",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Step-by-step chemical equations & quantitative calculations",
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      }
    }

    // Tabs Row
    item {
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(CalcTab.values()) { tab ->
          ElevatedFilterChip(
            selected = selectedTab == tab,
            onClick = {
              selectedTab = tab
              compute()
            },
            label = { Text("${tab.icon} ${tab.label}") },
            colors = FilterChipDefaults.elevatedFilterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
              selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
          )
        }
      }
    }

    // Interactive Inputs Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "${selectedTab.icon} ${selectedTab.label} Inputs",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )

          Spacer(modifier = Modifier.height(10.dp))

          when (selectedTab) {
            CalcTab.MOLAR_MASS -> {
              OutlinedTextField(
                value = formulaInput,
                onValueChange = { formulaInput = it; compute() },
                label = { Text("Chemical Formula") },
                placeholder = { Text("e.g. H2SO4, Ca(OH)2, C6H12O6") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
              )
              Spacer(modifier = Modifier.height(8.dp))
              LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                val presets = listOf("H2O", "NaCl", "HCl", "H2SO4", "Ca(OH)2", "C6H12O6", "CuSO4", "PbI2")
                items(presets) { preset ->
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.clickable { formulaInput = preset; compute() }
                  ) {
                    Text(
                      text = preset,
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                  }
                }
              }
            }
            CalcTab.MOLES -> {
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                  value = massInput,
                  onValueChange = { massInput = it; compute() },
                  label = { Text("Mass (g)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
                OutlinedTextField(
                  value = molarMassInput,
                  onValueChange = { molarMassInput = it; compute() },
                  label = { Text("Molar Mass (g/mol)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
              }
            }
            CalcTab.MOLARITY -> {
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                  value = molesInput,
                  onValueChange = { molesInput = it; compute() },
                  label = { Text("Moles of Solute (mol)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
                OutlinedTextField(
                  value = volumeInput,
                  onValueChange = { volumeInput = it; compute() },
                  label = { Text("Volume (Liters)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
              }
            }
            CalcTab.NORMALITY -> {
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                  value = molesInput,
                  onValueChange = { molesInput = it; compute() },
                  label = { Text("Molarity (M)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
                OutlinedTextField(
                  value = valencyInput,
                  onValueChange = { valencyInput = it; compute() },
                  label = { Text("n-factor / Valency") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
              }
            }
            CalcTab.DILUTION -> {
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                  value = m1Input,
                  onValueChange = { m1Input = it; compute() },
                  label = { Text("Stock M₁ (M)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
                OutlinedTextField(
                  value = v1Input,
                  onValueChange = { v1Input = it; compute() },
                  label = { Text("Initial V₁ (mL)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
                OutlinedTextField(
                  value = v2Input,
                  onValueChange = { v2Input = it; compute() },
                  label = { Text("Final V₂ (mL)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
              }
            }
            CalcTab.PH -> {
              OutlinedTextField(
                value = hConcInput,
                onValueChange = { hConcInput = it; compute() },
                label = { Text("Hydrogen Ion [H⁺] Concentration (M)") },
                placeholder = { Text("e.g. 0.001 or 1e-4") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
              )
            }
            CalcTab.GAS_LAW -> {
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                  value = pressureInput,
                  onValueChange = { pressureInput = it; compute() },
                  label = { Text("Pressure (atm)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
                OutlinedTextField(
                  value = gasVolumeInput,
                  onValueChange = { gasVolumeInput = it; compute() },
                  label = { Text("Volume (L)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
                OutlinedTextField(
                  value = tempKelvinInput,
                  onValueChange = { tempKelvinInput = it; compute() },
                  label = { Text("Temp (K)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
              }
            }
            CalcTab.DENSITY -> {
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                  value = densityMassInput,
                  onValueChange = { densityMassInput = it; compute() },
                  label = { Text("Mass (g)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
                OutlinedTextField(
                  value = densityVolInput,
                  onValueChange = { densityVolInput = it; compute() },
                  label = { Text("Volume (cm³ or mL)") },
                  modifier = Modifier.weight(1f),
                  singleLine = true
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = { compute() },
            modifier = Modifier
              .fillMaxWidth()
              .height(42.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Calculate Step-by-Step", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Step-by-Step Calculation Breakdown Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = currentResult.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.White)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Result Badge
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFF1E293B),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = "Calculated Result:",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
              )
              Text(
                text = currentResult.resultFormatted,
                style = MaterialTheme.typography.headlineSmall.copy(
                  color = Color(0xFF00E5FF),
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Formula
          Text(
            text = "Formula Applied: ${currentResult.formulaUsed}",
            style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.SemiBold)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Detailed Steps
          Text(
            text = "Step-by-Step Derivation:",
            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
          )
          Spacer(modifier = Modifier.height(6.dp))
          currentResult.steps.forEachIndexed { idx, step ->
            Row(
              modifier = Modifier.padding(vertical = 3.dp),
              verticalAlignment = Alignment.Top
            ) {
              Text(
                text = "${idx + 1}. ",
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
              )
              Text(
                text = step,
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFE2E8F0))
              )
            }
          }

          if (currentResult.explanation.isNotBlank()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Scientific Note: ${currentResult.explanation}",
              style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF94A3B8), lineHeight = 18.sp)
            )
          }
        }
      }
    }
  }
}
