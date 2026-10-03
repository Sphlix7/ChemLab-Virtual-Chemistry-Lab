package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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

/**
 * Animated Interactive Laboratory Glassware Renderer
 */
@Composable
fun LabGlasswareSim(
  apparatusType: String, // "beaker", "conical_flask", "test_tube"
  liquidColor: Color,
  liquidLevel: Float, // 0.0f to 1.0f
  isHeating: Boolean,
  hasBubbles: Boolean,
  hasPrecipitate: Boolean,
  precipitateColor: Color = Color.Yellow,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "liquid_anim")

  val bubbleOffset by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "bubbles"
  )

  val flameFlicker by infiniteTransition.animateFloat(
    initialValue = 0.85f,
    targetValue = 1.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(250, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "flame"
  )

  val wavePhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 6.28318f,
    animationSpec = infiniteRepeatable(
      animation = tween(2000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "waves"
  )

  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height
      val glassStroke = 3.dp.toPx()
      val glassColor = Color(0xCCBAE6FD)

      // If heating, draw Bunsen Burner flame underneath
      if (isHeating) {
        drawBunsenFlame(w, h, flameFlicker)
      }

      val glasswareTop = if (isHeating) h * 0.08f else h * 0.15f
      val glasswareBottom = if (isHeating) h * 0.72f else h * 0.85f
      val glassHeight = glasswareBottom - glasswareTop

      when (apparatusType.lowercase()) {
        "conical_flask" -> {
          drawConicalFlask(
            w = w,
            top = glasswareTop,
            bottom = glasswareBottom,
            liquidLevel = liquidLevel,
            liquidColor = liquidColor,
            glassStroke = glassStroke,
            glassColor = glassColor,
            wavePhase = wavePhase,
            hasBubbles = hasBubbles,
            bubblePhase = bubbleOffset,
            hasPrecipitate = hasPrecipitate,
            precipitateColor = precipitateColor
          )
        }
        "test_tube" -> {
          drawTestTube(
            w = w,
            top = glasswareTop,
            bottom = glasswareBottom,
            liquidLevel = liquidLevel,
            liquidColor = liquidColor,
            glassStroke = glassStroke,
            glassColor = glassColor,
            wavePhase = wavePhase,
            hasBubbles = hasBubbles,
            bubblePhase = bubbleOffset,
            hasPrecipitate = hasPrecipitate,
            precipitateColor = precipitateColor
          )
        }
        else -> {
          // Default: Beaker
          drawBeaker(
            w = w,
            top = glasswareTop,
            bottom = glasswareBottom,
            liquidLevel = liquidLevel,
            liquidColor = liquidColor,
            glassStroke = glassStroke,
            glassColor = glassColor,
            wavePhase = wavePhase,
            hasBubbles = hasBubbles,
            bubblePhase = bubbleOffset,
            hasPrecipitate = hasPrecipitate,
            precipitateColor = precipitateColor
          )
        }
      }
    }
  }
}

private fun DrawScope.drawBunsenFlame(w: Float, h: Float, flicker: Float) {
  val centerX = w / 2f
  val burnerTop = h * 0.82f
  val burnerBottom = h * 0.98f

  // Burner Base and Barrel
  drawRoundRect(
    color = Color(0xFF64748B),
    topLeft = Offset(centerX - 16.dp.toPx(), burnerTop),
    size = Size(32.dp.toPx(), burnerBottom - burnerTop),
    cornerRadius = CornerRadius(4.dp.toPx())
  )
  drawRoundRect(
    color = Color(0xFF334155),
    topLeft = Offset(centerX - 36.dp.toPx(), burnerBottom - 8.dp.toPx()),
    size = Size(72.dp.toPx(), 8.dp.toPx()),
    cornerRadius = CornerRadius(3.dp.toPx())
  )

  // Outer Flame (Soft orange/cyan glow)
  val flameHeight = 45.dp.toPx() * flicker
  val outerFlamePath = Path().apply {
    moveTo(centerX, burnerTop - flameHeight)
    cubicTo(
      centerX + 18.dp.toPx() * flicker, burnerTop - flameHeight * 0.5f,
      centerX + 12.dp.toPx(), burnerTop - 4.dp.toPx(),
      centerX, burnerTop
    )
    cubicTo(
      centerX - 12.dp.toPx(), burnerTop - 4.dp.toPx(),
      centerX - 18.dp.toPx() * flicker, burnerTop - flameHeight * 0.5f,
      centerX, burnerTop - flameHeight
    )
    close()
  }

  drawPath(
    path = outerFlamePath,
    brush = Brush.verticalGradient(
      colors = listOf(Color(0xFF38BDF8), Color(0xFFF97316), Color(0xFFEAB308)),
      startY = burnerTop - flameHeight,
      endY = burnerTop
    )
  )

  // Inner Cone (Hot Blue Cone)
  val innerFlameHeight = 22.dp.toPx() * flicker
  val innerFlamePath = Path().apply {
    moveTo(centerX, burnerTop - innerFlameHeight)
    cubicTo(
      centerX + 8.dp.toPx(), burnerTop - innerFlameHeight * 0.4f,
      centerX + 6.dp.toPx(), burnerTop,
      centerX, burnerTop
    )
    cubicTo(
      centerX - 6.dp.toPx(), burnerTop,
      centerX - 8.dp.toPx(), burnerTop - innerFlameHeight * 0.4f,
      centerX, burnerTop - innerFlameHeight
    )
    close()
  }

  drawPath(
    path = innerFlamePath,
    brush = Brush.verticalGradient(
      colors = listOf(Color(0xFF00E5FF), Color(0xFF2563EB)),
      startY = burnerTop - innerFlameHeight,
      endY = burnerTop
    )
  )
}

private fun DrawScope.drawBeaker(
  w: Float,
  top: Float,
  bottom: Float,
  liquidLevel: Float,
  liquidColor: Color,
  glassStroke: Float,
  glassColor: Color,
  wavePhase: Float,
  hasBubbles: Boolean,
  bubblePhase: Float,
  hasPrecipitate: Boolean,
  precipitateColor: Color
) {
  val left = w * 0.22f
  val right = w * 0.78f
  val beakerWidth = right - left
  val beakerHeight = bottom - top

  // Liquid
  if (liquidLevel > 0.05f) {
    val liquidHeight = beakerHeight * liquidLevel.coerceIn(0.05f, 0.92f)
    val liquidTop = bottom - liquidHeight

    val liquidPath = Path().apply {
      moveTo(left + glassStroke, bottom - 8.dp.toPx())
      lineTo(left + glassStroke, liquidTop)

      // Gentle wave surface
      val segments = 10
      val segWidth = (beakerWidth - 2 * glassStroke) / segments
      for (i in 0..segments) {
        val x = left + glassStroke + i * segWidth
        val y = liquidTop + sin(wavePhase + i * 0.8f) * 3.dp.toPx()
        lineTo(x, y)
      }

      lineTo(right - glassStroke, bottom - 8.dp.toPx())
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

    // Precipitate solid at bottom
    if (hasPrecipitate) {
      drawRoundRect(
        color = precipitateColor,
        topLeft = Offset(left + 8.dp.toPx(), bottom - 14.dp.toPx()),
        size = Size(beakerWidth - 16.dp.toPx(), 10.dp.toPx()),
        cornerRadius = CornerRadius(4.dp.toPx())
      )
    }

    // Bubbles
    if (hasBubbles) {
      drawBubbles(left, right, liquidTop, bottom, bubblePhase)
    }
  }

  // Beaker Glass Outline
  val beakerPath = Path().apply {
    // Spout at top left
    moveTo(left - 8.dp.toPx(), top)
    lineTo(left, top + 6.dp.toPx())
    lineTo(left, bottom - 10.dp.toPx())
    quadraticTo(left, bottom, left + 10.dp.toPx(), bottom)
    lineTo(right - 10.dp.toPx(), bottom)
    quadraticTo(right, bottom, right, bottom - 10.dp.toPx())
    lineTo(right, top + 4.dp.toPx())
    lineTo(right + 4.dp.toPx(), top)
  }

  drawPath(
    path = beakerPath,
    color = glassColor,
    style = Stroke(width = glassStroke, cap = StrokeCap.Round)
  )

  // Calibration graduation ticks
  for (i in 1..4) {
    val tickY = bottom - (beakerHeight * 0.2f * i)
    drawLine(
      color = Color(0x88FFFFFF),
      start = Offset(left + 4.dp.toPx(), tickY),
      end = Offset(left + (if (i % 2 == 0) 18.dp.toPx() else 12.dp.toPx()), tickY),
      strokeWidth = 2.dp.toPx()
    )
  }
}

private fun DrawScope.drawConicalFlask(
  w: Float,
  top: Float,
  bottom: Float,
  liquidLevel: Float,
  liquidColor: Color,
  glassStroke: Float,
  glassColor: Color,
  wavePhase: Float,
  hasBubbles: Boolean,
  bubblePhase: Float,
  hasPrecipitate: Boolean,
  precipitateColor: Color
) {
  val centerX = w / 2f
  val neckWidth = 24.dp.toPx()
  val neckBottom = top + (bottom - top) * 0.35f
  val baseLeft = w * 0.16f
  val baseRight = w * 0.84f

  // Liquid
  if (liquidLevel > 0.05f) {
    val totalHeight = bottom - top
    val liquidHeight = totalHeight * liquidLevel.coerceIn(0.05f, 0.85f)
    val liquidTop = bottom - liquidHeight

    val progress = (bottom - liquidTop) / (bottom - neckBottom)
    val curLeft = if (liquidTop > neckBottom) {
      baseLeft + (centerX - neckWidth / 2f - baseLeft) * (1f - progress.coerceIn(0f, 1f))
    } else {
      centerX - neckWidth / 2f
    }
    val curRight = if (liquidTop > neckBottom) {
      baseRight - (baseRight - (centerX + neckWidth / 2f)) * (1f - progress.coerceIn(0f, 1f))
    } else {
      centerX + neckWidth / 2f
    }

    val liquidPath = Path().apply {
      moveTo(baseLeft + 6.dp.toPx(), bottom - 8.dp.toPx())
      lineTo(curLeft, liquidTop)
      lineTo(curRight, liquidTop)
      lineTo(baseRight - 6.dp.toPx(), bottom - 8.dp.toPx())
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

    if (hasPrecipitate) {
      drawRoundRect(
        color = precipitateColor,
        topLeft = Offset(baseLeft + 16.dp.toPx(), bottom - 12.dp.toPx()),
        size = Size((baseRight - baseLeft) - 32.dp.toPx(), 8.dp.toPx()),
        cornerRadius = CornerRadius(4.dp.toPx())
      )
    }

    if (hasBubbles) {
      drawBubbles(curLeft, curRight, liquidTop, bottom, bubblePhase)
    }
  }

  // Flask Outline
  val flaskPath = Path().apply {
    // Neck lip top
    moveTo(centerX - neckWidth / 2f - 4.dp.toPx(), top)
    lineTo(centerX + neckWidth / 2f + 4.dp.toPx(), top)
    moveTo(centerX + neckWidth / 2f, top)
    lineTo(centerX + neckWidth / 2f, neckBottom)
    lineTo(baseRight, bottom - 8.dp.toPx())
    quadraticTo(baseRight, bottom, baseRight - 12.dp.toPx(), bottom)
    lineTo(baseLeft + 12.dp.toPx(), bottom)
    quadraticTo(baseLeft, bottom, baseLeft, bottom - 8.dp.toPx())
    lineTo(centerX - neckWidth / 2f, neckBottom)
    lineTo(centerX - neckWidth / 2f, top)
  }

  drawPath(
    path = flaskPath,
    color = glassColor,
    style = Stroke(width = glassStroke, cap = StrokeCap.Round)
  )
}

private fun DrawScope.drawTestTube(
  w: Float,
  top: Float,
  bottom: Float,
  liquidLevel: Float,
  liquidColor: Color,
  glassStroke: Float,
  glassColor: Color,
  wavePhase: Float,
  hasBubbles: Boolean,
  bubblePhase: Float,
  hasPrecipitate: Boolean,
  precipitateColor: Color
) {
  val centerX = w / 2f
  val tubeWidth = 32.dp.toPx()
  val left = centerX - tubeWidth / 2f
  val right = centerX + tubeWidth / 2f

  // Liquid
  if (liquidLevel > 0.05f) {
    val totalHeight = bottom - top
    val liquidHeight = totalHeight * liquidLevel.coerceIn(0.05f, 0.88f)
    val liquidTop = bottom - liquidHeight

    val liquidPath = Path().apply {
      moveTo(left + glassStroke, liquidTop)
      lineTo(right - glassStroke, liquidTop)
      lineTo(right - glassStroke, bottom - tubeWidth / 2f)
      arcTo(
        rect = androidx.compose.ui.geometry.Rect(
          left + glassStroke,
          bottom - tubeWidth + glassStroke,
          right - glassStroke,
          bottom - glassStroke
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 180f,
        forceMoveTo = false
      )
      lineTo(left + glassStroke, liquidTop)
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

    if (hasPrecipitate) {
      drawCircle(
        color = precipitateColor,
        radius = 8.dp.toPx(),
        center = Offset(centerX, bottom - 10.dp.toPx())
      )
    }

    if (hasBubbles) {
      drawBubbles(left, right, liquidTop, bottom, bubblePhase)
    }
  }

  // Tube Outline with rounded hemisphere bottom
  val tubePath = Path().apply {
    moveTo(left - 3.dp.toPx(), top)
    lineTo(right + 3.dp.toPx(), top)
    moveTo(right, top)
    lineTo(right, bottom - tubeWidth / 2f)
    arcTo(
      rect = androidx.compose.ui.geometry.Rect(left, bottom - tubeWidth, right, bottom),
      startAngleDegrees = 0f,
      sweepAngleDegrees = 180f,
      forceMoveTo = false
    )
    lineTo(left, top)
  }

  drawPath(
    path = tubePath,
    color = glassColor,
    style = Stroke(width = glassStroke, cap = StrokeCap.Round)
  )
}

private fun DrawScope.drawBubbles(left: Float, right: Float, top: Float, bottom: Float, phase: Float) {
  val centerX = (left + right) / 2f
  val spread = (right - left) * 0.6f

  val bubbleOffsets = listOf(
    Pair(-0.3f, 0.1f),
    Pair(0.2f, 0.4f),
    Pair(-0.1f, 0.7f),
    Pair(0.35f, 0.25f),
    Pair(-0.25f, 0.85f)
  )

  for ((xFactor, phaseOffset) in bubbleOffsets) {
    val progress = (phase + phaseOffset) % 1f
    val by = bottom - (bottom - top) * progress
    val bx = centerX + spread * xFactor + sin(progress * 12f) * 4.dp.toPx()
    val alpha = (1f - progress).coerceIn(0.2f, 0.9f)
    val r = (3.dp.toPx() + (progress * 2.dp.toPx()))

    drawCircle(
      color = Color.White.copy(alpha = alpha),
      radius = r,
      center = Offset(bx, by)
    )
  }
}
