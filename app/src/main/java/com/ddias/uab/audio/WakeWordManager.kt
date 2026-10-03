package com.ddias.uab.audio

import android.util.Log
import com.ddias.uab.speech.ITextToSpeech
import com.ddias.ultron.Core

class WakeWordManager(
    private val core: Core,
    private val textToSpeech: ITextToSpeech,
) {

    companion object {
        private const val WAKE_WORD = "Ultron"
    }

    fun process(speech: String) {
        Log.d("WakeWordManager", "speech $speech")
        val command = extractCommand(speech)
        processCommand(command)
    }

    private fun extractCommand(text: String): String {
        Log.d("WakeWordManager", "extractCommand: $text")
        val lowerText = text.lowercase()
        val wakeWordLower = WAKE_WORD.lowercase()

        if (lowerText.contains(wakeWordLower)) {
            return lowerText
                .substringAfter(delimiter = wakeWordLower, missingDelimiterValue = "")
                .trim()
        }
        return ""
    }

    private fun processCommand(command: String) {
        Log.d("WakeWordManager", "processCommand: $command")
        val response = core.process(command)
        Log.d("WakeWordManager", "core result: $response")
        textToSpeech.speak(response)
    }
}