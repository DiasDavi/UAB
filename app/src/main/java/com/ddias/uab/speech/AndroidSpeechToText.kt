package com.ddias.uab.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AndroidSpeechToText(
    private val context: Context
) : ISpeechToText {

    override suspend fun listenOnce(): SttResult = suspendCancellableCoroutine { continuation ->
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                if (continuation.isActive) {
                    val result = when (error) {
                        SpeechRecognizer.ERROR_NO_MATCH,
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> SttResult.Silence

                        else -> SttResult.Error(Exception("Erro STT: $error"))
                    }
                    safeDestroy(speechRecognizer)
                    continuation.resume(result)
                }
            }

            override fun onResults(results: Bundle?) {
                if (continuation.isActive) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val text = matches?.firstOrNull()

                    val result = if (!text.isNullOrBlank()) {
                        SttResult.Speech(text)
                    } else {
                        SttResult.Silence
                    }
                    safeDestroy(speechRecognizer)
                    continuation.resume(result)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        }

        try {
            speechRecognizer.startListening(intent)
        } catch (e: Exception) {
            if (continuation.isActive) {
                safeDestroy(speechRecognizer)
                continuation.resume(SttResult.Error(e))
            }
        }

        continuation.invokeOnCancellation {
            safeDestroy(speechRecognizer)
        }
    }

    private fun safeDestroy(recognizer: SpeechRecognizer) {
        try {
            recognizer.stopListening()
            recognizer.destroy()
        } catch (e: Exception) {
        }
    }
}

sealed interface SttResult {
    data class Speech(val text: String) : SttResult
    object Silence : SttResult
    data class Error(val throwable: Throwable) : SttResult
}