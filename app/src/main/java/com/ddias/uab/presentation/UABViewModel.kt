package com.ddias.uab.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ddias.uab.speech.ISpeechToText
import com.ddias.uab.speech.ITextToSpeech
import com.ddias.ultron.Core

class UABViewModel(
    private val core: Core,
    private val speechToText: ISpeechToText,
    private val textToSpeech: ITextToSpeech
) : ViewModel() {

    var isListening by mutableStateOf(false)
        private set

    var response by mutableStateOf("")
        private set

    fun startToListen() {
        isListening = true
        speechToText.startListening(
            onResult = { text ->
                response = core.process(text)
                textToSpeech.speak(response)
                isListening = false
            },
            onFailure = {
                response = "Falha na transcrição"
                isListening = false
                Log.d("SPEECH", "Transcription failed")
            },
        )
    }
}