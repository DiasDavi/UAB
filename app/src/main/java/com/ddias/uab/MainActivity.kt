package com.ddias.uab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddias.uab.ui.theme.UABTheme
import com.ddias.ultron.Core

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val core = Core()

        setContent {
            UABTheme {
                Greeting(
                    core = core
                )
            }
        }
    }
}

@Composable
fun Greeting(core: Core) {
    var input by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { newInput -> input = newInput },
            label = { Text(text = "Input") },
            placeholder = { Text(text = "Olá Ultron") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                response = core.process(input)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = response)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UABTheme {
        Greeting(core = Core())
    }
}