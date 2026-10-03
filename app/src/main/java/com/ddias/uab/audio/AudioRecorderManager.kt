package com.ddias.uab.audio

import android.util.Log
import com.ddias.uab.speech.ISpeechToText
import com.ddias.uab.speech.ISttResult
import kotlin.time.Duration.Companion.milliseconds

class AudioRecorderManager(
    private val androidSpeechToText: ISpeechToText
) {

    var currentSpeech: String? = null
        private set

    suspend fun startLoop(onSpeechDetected: suspend (String) -> Unit) {
        while (true) {
            when (val result = androidSpeechToText.listenOnce()) {
                is ISttResult.Speech -> {
                    Log.d("AudioRecorderManager", "result: $result")
                    currentSpeech = result.text

                    onSpeechDetected(result.text)

                    currentSpeech = null
                }

                is ISttResult.Silence -> {
                    currentSpeech = null
                }

                is ISttResult.Error -> {
                    currentSpeech = null
                    kotlinx.coroutines.delay(1000.milliseconds)
                }
            }
        }
    }
}