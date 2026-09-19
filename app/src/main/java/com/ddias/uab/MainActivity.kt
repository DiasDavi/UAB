package com.ddias.uab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ddias.uab.presentation.UABScreen
import com.ddias.uab.presentation.UABViewModel
import com.ddias.uab.speech.AndroidSpeechToText
import com.ddias.uab.speech.AndroidTextToSpeech
import com.ddias.uab.speech.ISpeechToText
import com.ddias.uab.speech.ITextToSpeech
import com.ddias.uab.ui.theme.UABTheme
import com.ddias.ultron.Core

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val core = Core()
        val androidSpeechToText: ISpeechToText = AndroidSpeechToText(this)
        val androidTextToSpeech: ITextToSpeech = AndroidTextToSpeech(this)

        val vm = UABViewModel(core, androidSpeechToText, androidTextToSpeech)



        setContent {
            UABTheme {
                UABScreen(
                    isListening = vm.isListening,
                    response = vm.response,
                    onListen = vm::startToListen
                )
            }
        }
    }
}