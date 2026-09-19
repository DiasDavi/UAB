package com.ddias.uab.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import android.util.Log
import java.util.Locale

class AndroidTextToSpeech(
    context: Context
) : ITextToSpeech {

    private var isInitialized = false
    private val tts: TextToSpeech  = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true

            tts.language = Locale("pt", "BR")

            val voice = tts.voices.firstOrNull { it.name == "pt-br-x-ptd-local" }

            if (voice != null) {
                tts.voice = voice
            }
        }
    }

    override fun speak(text: String) {
        if (!isInitialized) {
            Log.d("TTS", "TTS isn't initialized")
            return
        }
        tts.speak(text, QUEUE_FLUSH, null, null)
    }
}