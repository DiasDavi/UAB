package com.ddias.uab.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class AndroidSpeechToText(
    context: Context
): ISpeechToText {
    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    override fun startListening(
        onResult: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        speechRecognizer.setRecognitionListener(
            object : RecognitionListener {
                override fun onBeginningOfSpeech() {}

                override fun onBufferReceived(p0: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onError(p0: Int) {}

                override fun onEvent(p0: Int, p1: Bundle?) {}

                override fun onPartialResults(p0: Bundle?) {}

                override fun onReadyForSpeech(p0: Bundle?) {}

                override fun onResults(results: Bundle?) {
                    val recognitionResults = results?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )

                    val text = recognitionResults?.firstOrNull()

                    if (!text.isNullOrBlank()) {
                        onResult(text)
                    } else {
                        onFailure()
                    }
                }

                override fun onRmsChanged(p0: Float) {}
            }
        )

        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }

        speechRecognizer.startListening(speechIntent)
    }
}