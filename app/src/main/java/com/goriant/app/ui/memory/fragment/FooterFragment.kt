package com.goriant.app.ui.memory.fragment

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goriant.app.style.H2

class FooterFragment {

    @Composable
    fun Display(
        onHomeClickResetGame: () -> Unit,
        onRestartClick: () -> Unit,
        onSettingsClick: () -> Unit,
        onHelpClick: () -> Unit
    ) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp
        ) {
            IconButton(onClick = onHomeClickResetGame) {
                Text("🏠", fontSize = H2)
            }
            Spacer(Modifier.weight(1f))

            IconButton(onClick = onRestartClick) {
                Text("🔁", fontSize = 24.sp)
            }
            Spacer(Modifier.weight(1f))

            IconButton(onClick = onSettingsClick) {
                Text("⚙️", fontSize = 24.sp)
            }

            Spacer(Modifier.weight(1f))

            IconButton(onClick = onHelpClick) {
                Text("❓", fontSize = 24.sp)
            }
        }
    }
}