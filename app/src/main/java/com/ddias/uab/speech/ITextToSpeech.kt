package com.ddias.uab.speech

interface ITextToSpeech {
    /** Speaks [text] and returns only after the utterance has finished. */
    suspend fun speak(text: String)
}
