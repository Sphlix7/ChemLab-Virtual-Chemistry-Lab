package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.NotesData
import com.example.model.ChemistryNote
import com.example.viewmodel.AppScreen

@Composable
fun NotesScreen(
  onBack: () -> Unit,
  onNavigateToQuiz: () -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  val simplifiedMap = remember { mutableStateMapOf<String, Boolean>() }
  val exampleMap = remember { mutableStateMapOf<String, Boolean>() }

  val filteredNotes = remember(searchQuery) {
    if (searchQuery.isBlank()) NotesData.notes
    else NotesData.notes.filter {
      it.title.contains(searchQuery, ignoreCase = true) ||
      it.definition.contains(searchQuery, ignoreCase = true) ||
      it.category.contains(searchQuery, ignoreCase = true)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("notes_screen")
  ) {
    // Header Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack, modifier = Modifier.testTag("notes_back_button")) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
      }
      Column {
        Text(
          text = "Chemistry Revision Notes",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "Key formulas, definitions, exam tips & equations",
          style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    }

    // Search Box
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      placeholder = { Text("Search notes by topic or keyword...") },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp),
      shape = RoundedCornerShape(12.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
      ),
      singleLine = true
    )

    // Notes List
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 10.dp, horizontal = 0.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(filteredNotes) { note ->
        val isSimplified = simplifiedMap[note.id] == true
        val isExampleShown = exampleMap[note.id] == true

        NoteTopicCard(
          note = note,
          isSimplified = isSimplified,
          isExampleShown = isExampleShown,
          onToggleSimplified = { simplifiedMap[note.id] = !isSimplified },
          onToggleExample = { exampleMap[note.id] = !isExampleShown },
          onTestMe = onNavigateToQuiz
        )
      }
    }
  }
}

@Composable
fun NoteTopicCard(
  note: ChemistryNote,
  isSimplified: Boolean,
  isExampleShown: Boolean,
  onToggleSimplified: () -> Unit,
  onToggleExample: () -> Unit,
  onTestMe: () -> Unit,
  modifier: Modifier = Modifier
) {
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
            text = note.category,
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = note.title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Definition
      Text(
        text = note.definition,
        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 20.sp)
      )

      // "Explain Simply" Expansion Box
      AnimatedVisibility(visible = isSimplified) {
        Column(modifier = Modifier.padding(top = 10.dp)) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(modifier = Modifier.padding(12.dp)) {
              Text("✨", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Explain Simply:",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                )
                Text(
                  text = note.simpleExplanation,
                  style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp)
                )
              }
            }
          }
        }
      }

      // "Give Example" Expansion Box
      AnimatedVisibility(visible = isExampleShown) {
        Column(modifier = Modifier.padding(top = 10.dp)) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(modifier = Modifier.padding(12.dp)) {
              Text("💡", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Everyday Real-World Examples:",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                )
                note.examples.forEach { ex ->
                  Text(
                    text = "• $ex",
                    style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp)
                  )
                }
              }
            }
          }
        }
      }

      // Important Formulas Box
      if (note.keyFormulas.isNotEmpty()) {
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.surface,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Text(
              text = "Important Formulas:",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.height(4.dp))
            note.keyFormulas.forEach { f ->
              Text(text = "• $f", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
            }
          }
        }
      }

      // Chemical Equations
      if (note.equations.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.surface,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Text(
              text = "Key Equations:",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            )
            Spacer(modifier = Modifier.height(4.dp))
            note.equations.forEach { eq ->
              Text(text = "• $eq", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
            }
          }
        }
      }

      // Exam Tips
      if (note.examTips.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Column {
          Text(
            text = "🎯 Exam Tips & Common Traps:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
          )
          note.examTips.forEach { tip ->
            Text(
              text = "• $tip",
              style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3 Interactive Action Buttons: "Explain Simply", "Give Example", "Test Me"
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = onToggleSimplified,
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(if (isSimplified) "Hide" else "Explain Simply", fontSize = 11.sp, maxLines = 1)
        }

        OutlinedButton(
          onClick = onToggleExample,
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(if (isExampleShown) "Hide" else "Give Example", fontSize = 11.sp, maxLines = 1)
        }

        Button(
          onClick = onTestMe,
          modifier = Modifier.weight(0.9f),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
          Text("Test Me", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
