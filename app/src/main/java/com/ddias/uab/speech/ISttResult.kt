package com.ddias.uab.speech

sealed interface ISttResult {
    data class Speech(val text: String) : ISttResult
    object Silence : ISttResult
    data class Error(val throwable: Throwable) : ISttResult
}