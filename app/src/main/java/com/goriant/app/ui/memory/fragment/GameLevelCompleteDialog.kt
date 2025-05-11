package com.goriant.app.ui.memory.fragment

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class GameLevelCompleteDialog {

    @Composable
    fun Show(
        show: Boolean,
        onReplay: () -> Unit,
        onNextLevel: () -> Unit
    ) {
        if(show) {
            AlertDialog(
                onDismissRequest = {
                    onReplay()
                    onNextLevel() },
                title = { Text(text = "Level Complete!") },
                text = { Text(text = "Congratulations! You have completed the level.") },
                confirmButton = {
                    Button(onClick = {
                            onNextLevel()
                            onReplay() }
                    ) {
                        Text("Next Level")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = onReplay
                    ) {
                        Text("Replay")
                    }
                }
            )
        }
    }
}