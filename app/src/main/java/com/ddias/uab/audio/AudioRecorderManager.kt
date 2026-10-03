package com.ddias.uab.audio

import android.util.Log
import com.ddias.uab.speech.AndroidSpeechToText
import com.ddias.uab.speech.ISpeechToText
import com.ddias.uab.speech.SttResult
import kotlin.time.Duration.Companion.milliseconds

class AudioRecorderManager(
    private val androidSpeechToText: ISpeechToText
) {

    var currentSpeech: String? = null
        private set

    suspend fun startLoop(onSpeechDetected: suspend (String) -> Unit) {
        while (true) {
            when (val result = androidSpeechToText.listenOnce()) {
                is SttResult.Speech -> {
                    Log.d("AudioRecorderManager", "result: $result")
                    currentSpeech = result.text

                    onSpeechDetected(result.text)

                    currentSpeech = null
                }
                is SttResult.Silence -> {
                    currentSpeech = null
                }
                is SttResult.Error -> {
                    currentSpeech = null
                    kotlinx.coroutines.delay(1000.milliseconds)
                }
            }
        }
    }
}