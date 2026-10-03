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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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
import com.example.data.ExperimentData
import com.example.model.Difficulty
import com.example.model.Experiment
import com.example.model.ExperimentCategory
import com.example.viewmodel.ChemLabViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExperimentLibraryScreen(
  viewModel: ChemLabViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf<ExperimentCategory?>(null) }

  val filteredExperiments = remember(searchQuery, selectedCategory) {
    ExperimentData.experiments.filter { exp ->
      val matchesCat = selectedCategory == null || exp.category == selectedCategory
      val matchesQuery = searchQuery.isBlank() ||
        exp.title.contains(searchQuery, ignoreCase = true) ||
        exp.subcategory.contains(searchQuery, ignoreCase = true) ||
        exp.chemicals.any { it.contains(searchQuery, ignoreCase = true) }
      matchesCat && matchesQuery
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("experiment_library_screen")
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack, modifier = Modifier.testTag("exp_lib_back_button")) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
      }
      Text(
        text = "Experiment Library",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
      )
    }

    // Search Box
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      placeholder = { Text("Search by name, chemical, or subcategory...") },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)
        .testTag("experiment_search_input"),
      shape = RoundedCornerShape(12.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
      ),
      singleLine = true
    )

    // Category Filter Chips
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        FilterChip(
          selected = selectedCategory == null,
          onClick = { selectedCategory = null },
          label = { Text("All (${ExperimentData.experiments.size})") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
          )
        )
      }
      items(ExperimentCategory.values()) { category ->
        FilterChip(
          selected = selectedCategory == category,
          onClick = { selectedCategory = if (selectedCategory == category) null else category },
          label = { Text("${category.icon} ${category.displayName}") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
          )
        )
      }
    }

    // Experiments List
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(bottom = 24.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      if (filteredExperiments.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(40.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("🧪", fontSize = 40.sp)
              Spacer(modifier = Modifier.height(12.dp))
              Text("No matching experiments found.", style = MaterialTheme.typography.bodyLarge)
            }
          }
        }
      }

      items(filteredExperiments) { exp ->
        ExperimentLibraryCard(
          experiment = exp,
          onStart = {
            viewModel.startGuidedExperiment(exp)
          }
        )
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExperimentLibraryCard(
  experiment: Experiment,
  onStart: () -> Unit,
  modifier: Modifier = Modifier
) {
  val diffColor = when (experiment.difficulty) {
    Difficulty.BEGINNER -> Color(0xFF10B981)
    Difficulty.INTERMEDIATE -> Color(0xFFF59E0B)
    Difficulty.ADVANCED -> Color(0xFFEF4444)
  }

  Card(
    modifier = modifier.fillMaxWidth(),
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
          color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        ) {
          Text(
            text = "${experiment.category.icon} ${experiment.subcategory}",
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = diffColor.copy(alpha = 0.15f)
          ) {
            Text(
              text = experiment.difficulty.name.lowercase().replaceFirstChar { it.uppercase() },
              style = MaterialTheme.typography.labelSmall.copy(color = diffColor, fontWeight = FontWeight.Bold),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = "${experiment.timeMinutes}m",
              style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = experiment.title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = experiment.description,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
      )

      Spacer(modifier = Modifier.height(10.dp))
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Text(
            text = "Formula: ${experiment.reactionFormula}",
            style = MaterialTheme.typography.labelMedium.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.SemiBold
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Tags for Required Chemicals
      Text(
        text = "Required Chemicals:",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
      )
      Spacer(modifier = Modifier.height(4.dp))
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        experiment.chemicals.forEach { chem ->
          Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color(0x1A64748B)
          ) {
            Text(
              text = chem,
              style = MaterialTheme.typography.labelSmall,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
      Button(
        onClick = onStart,
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .testTag("start_exp_btn_${experiment.id}"),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.PlayArrow, contentDescription = null)
          Spacer(modifier = Modifier.width(6.dp))
          Text("Start Guided Experiment", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
