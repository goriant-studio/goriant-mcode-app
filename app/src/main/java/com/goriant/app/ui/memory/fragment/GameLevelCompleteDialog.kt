package com.goriant.app.ui.memory.fragment

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class GameLevelCompleteDialog {

    @Composable
    fun Show(
        onDismiss: () -> Unit,
        onNextLevel: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Level Complete!") },
            text = { Text(text = "Congratulations! You have completed the level.") },
            confirmButton = {
                Button(onClick = {
                    onNextLevel()
                    onDismiss()
                }) {
                    Text("Next Level")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("Close")
                }
            }
        )
    }
}