package com.ddias.uab.speech

interface ISpeechToText {
    suspend fun listenOnce(): SttResult
}