package com.ddias.uab.presentation

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddias.uab.R
import com.ddias.uab.ui.theme.UABTheme

@Composable
fun UABScreen(start: suspend () -> Unit) {

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Log.d("PERMISSIONS", "Permissão de microfone negada.")
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        start()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        IconButton(
            onClick = { },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFF1434A4),
                contentColor = Color(0xFFFFFFFF)
            ),
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_microphone),
                contentDescription = "Microphone",
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun UABScreenPreview() {
    UABTheme {
        UABScreen(start = {})
    }
}