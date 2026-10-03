package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TitrationApparatusCanvas
import com.example.viewmodel.ChemLabViewModel

@Composable
fun TitrationSimulatorScreen(
  viewModel: ChemLabViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val titrantAddedMl by viewModel.titrantAddedMl.collectAsState()
  val isTapOpen by viewModel.isTitrationTapOpen.collectAsState()
  val currentPh by viewModel.titrationPh.collectAsState()
  val flaskColor by viewModel.titrationFlaskColor.collectAsState()
  val isEndpointReached by viewModel.isEndpointReached.collectAsState()
  val curveData by viewModel.titrationCurveData.collectAsState()

  var selectedIndicator by remember { mutableStateOf("Phenolphthalein") }
  val indicators = listOf("Phenolphthalein", "Methyl Orange", "Bromothymol Blue")

  // Analyte details: 25.0 mL of 0.1 M HCl
  val acidMolarity = 0.1
  val acidVolume = 25.0
  val baseMolarity = 0.1

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("titration_simulator_screen"),
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
          IconButton(onClick = onBack, modifier = Modifier.testTag("titration_back_button")) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
          Column {
            Text(
              text = "Titration Laboratory",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Volumetric Neutralization & pH Curve",
              style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }

        IconButton(
          onClick = { viewModel.resetTitration() },
          modifier = Modifier.testTag("reset_titration_button")
        ) {
          Icon(Icons.Default.CleaningServices, contentDescription = "Reset Titration", tint = MaterialTheme.colorScheme.primary)
        }
      }
    }

    // Apparatus Viewport Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Top status row
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
                text = "Burette: 0.1 M NaOH | Flask: 25mL 0.1 M HCl",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isEndpointReached) Color(0x3310B981) else Color(0x2238BDF8)
            ) {
              Text(
                text = if (isEndpointReached) "Endpoint Reached! 🎯" else "Titrating...",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = if (isEndpointReached) Color(0xFF10B981) else Color(0xFF38BDF8),
                  fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Apparatus Graphic
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(240.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFF090D16)),
            contentAlignment = Alignment.Center
          ) {
            TitrationApparatusCanvas(
              buretteTotalMl = 50f,
              buretteAddedMl = titrantAddedMl,
              isDispensing = isTapOpen,
              flaskColor = flaskColor,
              modifier = Modifier.fillMaxSize()
            )

            // Live HUD sensors
            Column(
              modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xDD1E293B)
              ) {
                Text(
                  text = "Volume Added: ${"%.1f".format(titrantAddedMl)} mL",
                  style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }

              val phColor = when {
                currentPh < 6.8 -> Color(0xFFEF4444)
                currentPh > 7.5 -> Color(0xFF3B82F6)
                else -> Color(0xFF10B981)
              }
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xDD1E293B)
              ) {
                Text(
                  text = "pH: ${"%.2f".format(currentPh)}",
                  style = MaterialTheme.typography.labelSmall.copy(color = phColor, fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Burette Controls: Stopcock tap & Drop button
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { viewModel.toggleTitrationTap() },
              modifier = Modifier
                .weight(1.2f)
                .testTag("toggle_titration_tap_button"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isTapOpen) Color(0xFFEF4444) else Color(0xFF0284C7)
              )
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(if (isTapOpen) Icons.Default.Stop else Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isTapOpen) "Close Stopcock" else "Open Continuous Tap", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }

            OutlinedButton(
              onClick = { viewModel.addSingleDrop() },
              modifier = Modifier
                .weight(0.8f)
                .testTag("add_drop_button"),
              shape = RoundedCornerShape(10.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.WaterDrop, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("+0.1 mL Drop", fontSize = 12.sp)
              }
            }
          }
        }
      }
    }

    // Indicator Selector
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Select Chemical Indicator:",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(indicators) { ind ->
            ElevatedFilterChip(
              selected = selectedIndicator == ind,
              onClick = { selectedIndicator = ind },
              label = { Text(ind) },
              colors = FilterChipDefaults.elevatedFilterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
              )
            )
          }
        }
      }
    }

    // Real-Time pH Titration Curve Canvas
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
              text = "Titration Curve (pH vs Volume)",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Equivalence @ 25.0 mL",
              style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Canvas Graph Plotting
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(170.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFF0F172A))
              .padding(12.dp)
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height

              // Grid lines for pH 7 and pH 14
              val yPh7 = h * (1f - 7f / 14f)
              val yPh14 = 0f
              val xEq = w * (25f / 50f)

              // Equivalence dotted vertical line
              drawLine(
                color = Color(0x5538BDF8),
                start = Offset(xEq, 0f),
                end = Offset(xEq, h),
                strokeWidth = 1.dp.toPx()
              )

              // Neutral pH 7 horizontal guide
              drawLine(
                color = Color(0x33FFFFFF),
                start = Offset(0f, yPh7),
                end = Offset(w, yPh7),
                strokeWidth = 1.dp.toPx()
              )

              // Plot the titration curve data points
              if (curveData.isNotEmpty()) {
                val path = Path()
                curveData.forEachIndexed { index, (vol, ph) ->
                  val x = (vol / 50f) * w
                  val y = h * (1f - (ph / 14f).coerceIn(0f, 1f))
                  if (index == 0) {
                    path.moveTo(x, y)
                  } else {
                    path.lineTo(x, y)
                  }
                }

                drawPath(
                  path = path,
                  color = Color(0xFF00E5FF),
                  style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                // Current point marker
                val lastPoint = curveData.last()
                val curX = (lastPoint.first / 50f) * w
                val curY = h * (1f - (lastPoint.second / 14f).coerceIn(0f, 1f))

                drawCircle(
                  color = Color(0xFFF43F5E),
                  radius = 5.dp.toPx(),
                  center = Offset(curX, curY)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("0 mL (Pure Acid)", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            Text("25 mL (Neutral)", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold))
            Text("50 mL (Excess Base)", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
          }
        }
      }
    }

    // Automatic Stoichiometric Calculation: M1*V1 = M2*V2
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
            text = "Stoichiometric Volumetric Calculation",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )

          Spacer(modifier = Modifier.height(8.dp))

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = "M₁V₁ = M₂V₂  ⟹  Moles of Acid = Moles of Base",
                style = MaterialTheme.typography.titleMedium.copy(
                  color = MaterialTheme.colorScheme.primary,
                  fontWeight = FontWeight.Bold
                )
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "• M₁ (Acid Molarity) = $acidMolarity M HCl",
                style = MaterialTheme.typography.bodySmall
              )
              Text(
                text = "• V₁ (Acid Sample Volume) = $acidVolume mL",
                style = MaterialTheme.typography.bodySmall
              )
              Text(
                text = "• M₂ (Standard Titrant Molarity) = $baseMolarity M NaOH",
                style = MaterialTheme.typography.bodySmall
              )
              Text(
                text = "• V₂ (Burette Volume Consumed) = ${"%.1f".format(titrantAddedMl)} mL",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Variable Explanations:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "• M₁: Concentration of the unknown or sample analyte in moles per liter.\n• V₁: Known measured aliquot pipetted into the conical flask.\n• M₂: Standard concentration of the titrant in the burette.\n• V₂: Titrant volume required to reach the exact stoichiometric equivalence point.",
            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      }
    }
  }
}
