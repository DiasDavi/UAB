package com.ddias.uab.speech

interface ISpeechToText {
    fun startListening(
        onResult: (String) -> Unit,
        onFailure: () -> Unit
    )
}