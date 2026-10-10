package com.ddias.uab.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ddias.uab.audio.AudioRecorderManager
import com.ddias.uab.audio.WakeWordManager

class UABViewModel(
    private val audioRecorderManager: AudioRecorderManager,
    private val wakeWordManager: WakeWordManager
) : ViewModel() {

    suspend fun start() {
        audioRecorderManager.startLoop { speech ->
            wakeWordManager.process(speech)
        }
    }
}