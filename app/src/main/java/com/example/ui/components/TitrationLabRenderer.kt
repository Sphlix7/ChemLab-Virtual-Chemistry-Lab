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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun TitrationApparatusCanvas(
  buretteTotalMl: Float = 50f,
  buretteAddedMl: Float,
  isDispensing: Boolean,
  flaskColor: Color,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "titration_drops")

  val dropPhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(400, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "drop_phase"
  )

  val stirPhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 6.28318f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "stir_phase"
  )

  Box(modifier = modifier) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Retort Stand (Base & Vertical Iron Rod)
      val standBaseX = w * 0.15f
      val rodX = w * 0.28f
      val rodTop = h * 0.05f
      val rodBottom = h * 0.95f

      // Heavy Stand Base
      drawRoundRect(
        color = Color(0xFF334155),
        topLeft = Offset(w * 0.12f, h * 0.93f),
        size = Size(w * 0.76f, 14.dp.toPx()),
        cornerRadius = CornerRadius(4.dp.toPx())
      )

      // Iron Rod
      drawLine(
        color = Color(0xFF64748B),
        start = Offset(rodX, rodTop),
        end = Offset(rodX, rodBottom),
        strokeWidth = 6.dp.toPx(),
        cap = StrokeCap.Round
      )

      // Burette Clamp attached to rod
      val clampY = h * 0.28f
      drawLine(
        color = Color(0xFF475569),
        start = Offset(rodX, clampY),
        end = Offset(w * 0.58f, clampY),
        strokeWidth = 4.dp.toPx()
      )
      drawCircle(
        color = Color(0xFF94A3B8),
        radius = 5.dp.toPx(),
        center = Offset(rodX, clampY)
      )

      // Burette Column
      val buretteCenterX = w * 0.58f
      val buretteTop = h * 0.08f
      val buretteBodyBottom = h * 0.52f
      val buretteTipBottom = h * 0.62f
      val buretteWidth = 14.dp.toPx()

      drawBurette(
        centerX = buretteCenterX,
        top = buretteTop,
        bodyBottom = buretteBodyBottom,
        tipBottom = buretteTipBottom,
        width = buretteWidth,
        totalMl = buretteTotalMl,
        addedMl = buretteAddedMl,
        isDispensing = isDispensing,
        dropPhase = dropPhase
      )

      // Conical Flask below burette tip
      val flaskTop = h * 0.65f
      val flaskBottom = h * 0.92f
      drawTitrationFlask(
        centerX = buretteCenterX,
        top = flaskTop,
        bottom = flaskBottom,
        liquidColor = flaskColor,
        stirPhase = stirPhase,
        addedVolume = buretteAddedMl
      )
    }
  }
}

private fun DrawScope.drawBurette(
  centerX: Float,
  top: Float,
  bodyBottom: Float,
  tipBottom: Float,
  width: Float,
  totalMl: Float,
  addedMl: Float,
  isDispensing: Boolean,
  dropPhase: Float
) {
  val left = centerX - width / 2f
  val right = centerX + width / 2f
  val remainingRatio = ((totalMl - addedMl) / totalMl).coerceIn(0f, 1f)
  val liquidTop = top + (bodyBottom - top) * (1f - remainingRatio)

  // Liquid inside burette
  if (remainingRatio > 0.02f) {
    drawRect(
      brush = Brush.verticalGradient(
        colors = listOf(Color(0x8867E8F9), Color(0xCC0284C7)),
        startY = liquidTop,
        endY = bodyBottom
      ),
      topLeft = Offset(left + 2.dp.toPx(), liquidTop),
      size = Size(width - 4.dp.toPx(), bodyBottom - liquidTop)
    )
  }

  // Burette Glass Tube
  drawRect(
    color = Color(0xCCBAE6FD),
    topLeft = Offset(left, top),
    size = Size(width, bodyBottom - top),
    style = Stroke(width = 2.dp.toPx())
  )

  // Calibrated Volume Marks
  for (i in 0..10) {
    val tickY = top + (bodyBottom - top) * (i / 10f)
    drawLine(
      color = Color(0xFF0F172A),
      start = Offset(left, tickY),
      end = Offset(left + (if (i % 5 == 0) 6.dp.toPx() else 3.dp.toPx()), tickY),
      strokeWidth = 1.dp.toPx()
    )
  }

  // Stopcock Valve
  val valveY = bodyBottom + 8.dp.toPx()
  drawCircle(
    color = if (isDispensing) Color(0xFF10B981) else Color(0xFFEF4444),
    radius = 5.dp.toPx(),
    center = Offset(centerX, valveY)
  )
  // Stopcock handle
  val handleAngle = if (isDispensing) 0f else 90f
  drawLine(
    color = Color(0xFF1E293B),
    start = Offset(centerX - 8.dp.toPx(), valveY),
    end = Offset(centerX + 8.dp.toPx(), valveY),
    strokeWidth = 3.dp.toPx(),
    cap = StrokeCap.Round
  )

  // Tapered Delivery Tip
  val tipPath = Path().apply {
    moveTo(left + 3.dp.toPx(), bodyBottom)
    lineTo(centerX - 2.dp.toPx(), tipBottom)
    lineTo(centerX + 2.dp.toPx(), tipBottom)
    lineTo(right - 3.dp.toPx(), bodyBottom)
  }
  drawPath(path = tipPath, color = Color(0xCCBAE6FD), style = Stroke(width = 2.dp.toPx()))

  // Falling drops when dispensing
  if (isDispensing) {
    val dropY = tipBottom + (tipBottom * 0.15f * dropPhase)
    drawCircle(
      color = Color(0xEE0284C7),
      radius = 2.5.dp.toPx(),
      center = Offset(centerX, dropY)
    )
  }
}

private fun DrawScope.drawTitrationFlask(
  centerX: Float,
  top: Float,
  bottom: Float,
  liquidColor: Color,
  stirPhase: Float,
  addedVolume: Float
) {
  val neckWidth = 22.dp.toPx()
  val neckBottom = top + 18.dp.toPx()
  val baseHalfWidth = 44.dp.toPx()
  val baseLeft = centerX - baseHalfWidth
  val baseRight = centerX + baseHalfWidth

  // Liquid Level scales up as volume is added
  val fillProgress = (0.35f + (addedVolume / 50f) * 0.35f).coerceIn(0.2f, 0.75f)
  val liquidHeight = (bottom - neckBottom) * fillProgress
  val liquidTop = bottom - liquidHeight

  val curLeft = baseLeft + (centerX - neckWidth / 2f - baseLeft) * (1f - fillProgress)
  val curRight = baseRight - (baseRight - (centerX + neckWidth / 2f)) * (1f - fillProgress)

  // Liquid Body
  val liquidPath = Path().apply {
    moveTo(baseLeft + 6.dp.toPx(), bottom - 6.dp.toPx())
    lineTo(curLeft, liquidTop)
    // Swirling meniscus center dip
    val dip = sin(stirPhase) * 2.dp.toPx()
    quadraticTo(centerX, liquidTop + 4.dp.toPx() + dip, curRight, liquidTop)
    lineTo(baseRight - 6.dp.toPx(), bottom - 6.dp.toPx())
    close()
  }

  drawPath(
    path = liquidPath,
    brush = Brush.verticalGradient(
      colors = listOf(liquidColor.copy(alpha = 0.75f), liquidColor.copy(alpha = 0.95f)),
      startY = liquidTop,
      endY = bottom
    )
  )

  // Magnetic Stirrer Bar at bottom
  val stirOffsetX = sin(stirPhase) * 6.dp.toPx()
  drawRoundRect(
    color = Color.White,
    topLeft = Offset(centerX - 8.dp.toPx() + stirOffsetX, bottom - 10.dp.toPx()),
    size = Size(16.dp.toPx(), 5.dp.toPx()),
    cornerRadius = CornerRadius(2.dp.toPx())
  )

  // Glass Outline
  val flaskPath = Path().apply {
    moveTo(centerX - neckWidth / 2f - 3.dp.toPx(), top)
    lineTo(centerX + neckWidth / 2f + 3.dp.toPx(), top)
    moveTo(centerX + neckWidth / 2f, top)
    lineTo(centerX + neckWidth / 2f, neckBottom)
    lineTo(baseRight, bottom - 6.dp.toPx())
    quadraticTo(baseRight, bottom, baseRight - 10.dp.toPx(), bottom)
    lineTo(baseLeft + 10.dp.toPx(), bottom)
    quadraticTo(baseLeft, bottom, baseLeft, bottom - 6.dp.toPx())
    lineTo(centerX - neckWidth / 2f, neckBottom)
    lineTo(centerX - neckWidth / 2f, top)
  }

  drawPath(
    path = flaskPath,
    color = Color(0xCCBAE6FD),
    style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
  )
}
