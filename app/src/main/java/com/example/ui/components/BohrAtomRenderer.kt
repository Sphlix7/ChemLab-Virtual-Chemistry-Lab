package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun BohrAtomCanvas(
  symbol: String,
  shells: List<Int>,
  categoryColor: Color,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "atom_rotation")
  val rotationAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(12000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "electron_orbit"
  )

  Box(modifier = modifier) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val center = Offset(size.width / 2f, size.height / 2f)
      val maxRadius = min(size.width, size.height) * 0.44f

      // Nucleus
      val nucleusRadius = 14.dp.toPx()
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(Color.White, categoryColor, categoryColor.copy(alpha = 0.8f)),
          center = center,
          radius = nucleusRadius
        ),
        radius = nucleusRadius,
        center = center
      )

      val numShells = shells.size.coerceAtLeast(1)
      val shellStep = (maxRadius - nucleusRadius - 8.dp.toPx()) / numShells

      shells.forEachIndexed { index, electronCount ->
        val shellRadius = nucleusRadius + 8.dp.toPx() + (index + 1) * shellStep

        // Shell orbit path
        drawCircle(
          color = Color(0x4464748B),
          radius = shellRadius,
          center = center,
          style = Stroke(width = 1.dp.toPx())
        )

        // Orbiting electrons
        val direction = if (index % 2 == 0) 1 else -1
        val baseAngle = Math.toRadians((rotationAngle * direction * (1.0 + index * 0.3)).toDouble())

        for (e in 0 until electronCount) {
          val electronAngle = baseAngle + (2.0 * Math.PI * e / electronCount)
          val ex = center.x + (shellRadius * cos(electronAngle)).toFloat()
          val ey = center.y + (shellRadius * sin(electronAngle)).toFloat()

          // Electron glow
          drawCircle(
            color = Color(0x6638BDF8),
            radius = 4.dp.toPx(),
            center = Offset(ex, ey)
          )
          // Electron core
          drawCircle(
            color = Color(0xFF00E5FF),
            radius = 2.5.dp.toPx(),
            center = Offset(ex, ey)
          )
        }
      }
    }
  }
}
