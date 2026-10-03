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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PeriodicTableData
import com.example.model.ChemicalElement
import com.example.model.ElementCategory
import com.example.ui.components.BohrAtomCanvas

@OptIn(ExperimentalLayoutApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun PeriodicTableScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf<ElementCategory?>(null) }
  var selectedElement by remember { mutableStateOf<ChemicalElement?>(null) }
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  val filteredElements = remember(searchQuery, selectedCategory) {
    PeriodicTableData.elements.filter { elem ->
      val matchesCat = selectedCategory == null || elem.category == selectedCategory
      val matchesQuery = searchQuery.isBlank() ||
        elem.name.contains(searchQuery, ignoreCase = true) ||
        elem.symbol.contains(searchQuery, ignoreCase = true) ||
        elem.atomicNumber.toString() == searchQuery.trim()
      matchesCat && matchesQuery
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("periodic_table_screen")
  ) {
    // Header Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack, modifier = Modifier.testTag("periodic_table_back_button")) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
      }
      Column {
        Text(
          text = "Interactive Periodic Table",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "Explore atomic structure, Bohr electron shells & properties",
          style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    }

    // Search Box
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      placeholder = { Text("Search by name, symbol (Fe, Au), or atomic number...") },
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

    // Category Filter Chips
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        ElevatedFilterChip(
          selected = selectedCategory == null,
          onClick = { selectedCategory = null },
          label = { Text("All Elements") }
        )
      }
      items(ElementCategory.values()) { category ->
        ElevatedFilterChip(
          selected = selectedCategory == category,
          onClick = { selectedCategory = if (selectedCategory == category) null else category },
          label = { Text(category.label) },
          colors = FilterChipDefaults.elevatedFilterChipColors(
            selectedContainerColor = Color(category.colorHex).copy(alpha = 0.2f),
            selectedLabelColor = Color(category.colorHex)
          )
        )
      }
    }

    // Elements Grid
    LazyVerticalGrid(
      columns = GridCells.Adaptive(minSize = 82.dp),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp, top = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.weight(1f)
    ) {
      items(filteredElements) { elem ->
        ElementGridTile(
          element = elem,
          onClick = { selectedElement = elem }
        )
      }
    }
  }

  // Element Detail Bottom Sheet
  if (selectedElement != null) {
    val elem = selectedElement!!
    ModalBottomSheet(
      onDismissRequest = { selectedElement = null },
      sheetState = sheetState,
      containerColor = MaterialTheme.colorScheme.surface
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 8.dp),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Element Header Card
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(54.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(Color(elem.category.colorHex).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = elem.symbol,
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(elem.category.colorHex)
                  )
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Text(
                  text = elem.name,
                  style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                  text = "${elem.category.label} • Group ${elem.group}, Period ${elem.period}",
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.surfaceVariant
            ) {
              Text(
                text = "Z = ${elem.atomicNumber}",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }

        // Bohr Atom Orbiting Shells Visual Canvas
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
          ) {
            Column(
              modifier = Modifier.padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "Bohr Electron Orbit Shells (K, L, M...): ${elem.shells.joinToString(", ")}",
                style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
              )
              Spacer(modifier = Modifier.height(10.dp))
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(190.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(Color(0xFF090D16)),
                contentAlignment = Alignment.Center
              ) {
                BohrAtomCanvas(
                  symbol = elem.symbol,
                  shells = elem.shells,
                  categoryColor = Color(elem.category.colorHex),
                  modifier = Modifier.fillMaxSize()
                )
              }
            }
          }
        }

        // Atomic Specifications Grid
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
          ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
              Text(
                text = "Atomic Properties",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )

              Row(modifier = Modifier.fillMaxWidth()) {
                PropertyRow("Atomic Mass", "${elem.atomicMass} u", Modifier.weight(1f))
                PropertyRow("Valency", "${elem.valency}", Modifier.weight(1f))
              }
              Row(modifier = Modifier.fillMaxWidth()) {
                PropertyRow("Electron Config", elem.electronicConfig, Modifier.weight(1f))
                PropertyRow("Block", "${elem.block}-block", Modifier.weight(1f))
              }
              Row(modifier = Modifier.fillMaxWidth()) {
                PropertyRow("Melting Point", "${elem.meltingPointK} K", Modifier.weight(1f))
                PropertyRow("Boiling Point", "${elem.boilingPointK} K", Modifier.weight(1f))
              }
              Row(modifier = Modifier.fillMaxWidth()) {
                PropertyRow("Density", "${elem.densityGcm3} g/cm³", Modifier.weight(1f))
                PropertyRow("Shell Count", "${elem.shells.size} energy levels", Modifier.weight(1f))
              }
            }
          }
        }

        // Common Uses & Compounds
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
          ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
              Text(
                text = "Real-World Applications & Compounds",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "• Common Uses: ${elem.commonUses}",
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp)
              )
              Text(
                text = "• Key Compounds: ${elem.commonCompounds}",
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun ElementGridTile(
  element: ChemicalElement,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val catColor = Color(element.category.colorHex)

  Card(
    modifier = modifier
      .height(86.dp)
      .clickable { onClick() }
      .testTag("element_tile_${element.symbol}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(6.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "${element.atomicNumber}",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
        Box(
          modifier = Modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(catColor)
        )
      }

      Text(
        text = element.symbol,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = catColor
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )

      Text(
        text = element.name,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
        maxLines = 1,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Composable
private fun PropertyRow(label: String, value: String, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
    Text(text = value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
  }
}
