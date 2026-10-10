package com.ddias.uab.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

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

    override suspend fun speak(text: String) {
        if (!isInitialized) {
            Log.d("TTS", "TTS isn't initialized")
            return
        }

        suspendCancellableCoroutine { continuation ->
            val utteranceId = "uab-${System.nanoTime()}"
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(id: String?) = Unit

                override fun onDone(id: String?) {
                    if (id == utteranceId && continuation.isActive) {
                        continuation.resume(Unit)
                    }
                }

                override fun onError(id: String?) {
                    if (id == utteranceId && continuation.isActive) {
                        continuation.resume(Unit)
                    }
                }
            })

            val result = tts.speak(text, QUEUE_FLUSH, null, utteranceId)
            if (result == TextToSpeech.ERROR && continuation.isActive) {
                continuation.resume(Unit)
            }
        }
    }
}
