package com.ddias.uab.audio

import android.util.Log
import com.ddias.uab.speech.ITextToSpeech
import com.ddias.ultron.Core
import com.ddias.ultron.config.AssistentConfig.WAKE_WORD

class WakeWordManager(
    private val core: Core,
    private val textToSpeech: ITextToSpeech,
) {

    suspend fun process(speech: String) {
        Log.d("WakeWordManager", "speech $speech")
        val command = extractCommand(speech)
        if (command.isNotEmpty()) {
            processCommand(command)
        }
    }

    private fun extractCommand(text: String): String {
        val lowerText = text.lowercase()
        val wakeWordLower = WAKE_WORD.lowercase()

        if (lowerText.contains(wakeWordLower)) {
            return lowerText
        }
        return ""
    }

    private suspend fun processCommand(command: String) {
        val response = core.process(command)
        Log.d("WakeWordManager", "core result: $response")
        textToSpeech.speak(response)
    }
}
