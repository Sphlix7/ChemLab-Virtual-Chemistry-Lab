package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class VoiceNarrator(context: Context) {
  private var tts: TextToSpeech? = null
  private var isInitialized = false

  init {
    tts = TextToSpeech(context.applicationContext) { status ->
      if (status == TextToSpeech.SUCCESS) {
        tts?.language = Locale.US
        tts?.setSpeechRate(0.95f)
        tts?.setPitch(1.0f)
        isInitialized = true
      }
    }
  }

  fun speak(text: String) {
    if (isInitialized && text.isNotBlank()) {
      tts?.stop()
      tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "chemlab_utterance")
    }
  }

  fun stop() {
    tts?.stop()
  }

  fun shutdown() {
    tts?.stop()
    tts?.shutdown()
  }
}
