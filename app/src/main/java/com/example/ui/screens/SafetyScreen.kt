package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HazardItem(
  val symbol: String,
  val title: String,
  val description: String,
  val color: Color
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SafetyScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val hazards = listOf(
    HazardItem("☣️", "Biohazard / Toxic", "Hazardous if inhaled, swallowed, or absorbed through skin. Use in fume hoods.", Color(0xFFEF4444)),
    HazardItem("🔥", "Flammable Liquid", "Emits flammable vapors. Keep away from Bunsen flames; use electric water baths.", Color(0xFFF97316)),
    HazardItem("🧪", "Corrosive Substances", "Causes severe skin burns and eye damage (e.g. conc. HCl, NaOH). Use acid-resistant gloves.", Color(0xFFEAB308)),
    HazardItem("⭕", "Oxidizing Agents", "Accelerates combustion of other materials (e.g. nitrates, perchlorates, peroxides).", Color(0xFF3B82F6)),
    HazardItem("⚠️", "Irritant / Sensitizer", "Causes skin itching or respiratory discomfort. Ensure proper ventilation.", Color(0xFF10B981)),
    HazardItem("💥", "Explosive Risk", "Unstable under heat, mechanical shock, or high localized pressure.", Color(0xFFEC4899))
  )

  val rules = listOf(
    "1. Always Wear Eye Protection (PPE)" to "Safety goggles with side shields must be worn at all times in the laboratory area, even during setup or clean-up.",
    "2. The Golden Rule of Acid Dilution" to "Always Add Acid to Water (A&A). Slowly pour concentrated acid into water with continuous stirring. Never pour water into concentrated acid—the intense localized heat of hydration will flash-boil and spit acid droplets!",
    "3. Never Taste or Pipette by Mouth" to "Never mouth-pipette solutions; always use a rubber pipette bulb or mechanical suction pipetter.",
    "4. Wafting Technique for Odors" to "Never sniff directly from the mouth of a vessel. Gently wave your hand over the container toward your nose to detect aromas safely.",
    "5. Chemical Waste Segregation" to "Never dump heavy metals (lead, barium, silver) or halogenated organic solvents down the regular sink drain. Use labeled specialized waste jugs."
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("safety_screen"),
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
        IconButton(onClick = onBack, modifier = Modifier.testTag("safety_back_button")) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Column {
          Text(
            text = "Laboratory Safety & Hazchem",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "PPE guidelines, GHS pictograms, and emergency protocols",
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      }
    }

    // Important Safety Highlight Banner
    item {
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF2D1217)
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFFF87171), modifier = Modifier.size(28.dp))
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Safety First in Every Experiment",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFF87171))
            )
            Text(
              text = "ChemLab simulations follow standard academic lab protocols. Practice virtual experiments to understand hazards before entering real labs.",
              style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFCBD5E1), lineHeight = 18.sp)
            )
          }
        }
      }
    }

    // GHS Hazard Pictograms Grid
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "GHS Hazard Classification Symbols",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
          maxItemsInEachRow = 2
        ) {
          hazards.forEach { hazard ->
            Card(
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(hazard.symbol, fontSize = 22.sp)
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = hazard.title,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = hazard.color)
                  )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = hazard.description,
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
              }
            }
          }
        }
      }
    }

    // Core Safety Rules
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Fundamental Chemical Safety Rules",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))

        rules.forEach { (title, description) ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp)
              )
            }
          }
        }
      }
    }
  }
}
