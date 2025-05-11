package com.goriant.app.ui.memory.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goriant.app.style.H2
import com.goriant.app.style.H3
import com.goriant.app.style.TEXT_COLOR_SECONDARY

class GameTutorialDialog {

    @Composable
    fun Show(showTutorialState: MutableState<Boolean>) {
        val space = 10.dp
        if (showTutorialState.value) {
            AlertDialog(
                onDismissRequest = { showTutorialState.value = false },
                title = { Text("How to Play", color = TEXT_COLOR_SECONDARY, fontSize = H2, modifier = Modifier.padding(20.dp)) },
                text = {
                    Column {
                        Text("🍓 Match all pairs before time runs out!", fontSize = H3)
                        Spacer(Modifier.height(space))
                        Text("👆 Tap two cards to flip them.", fontSize = H3)
                        Spacer(Modifier.height(space))
                        Text("✅ If they match, they will remain flipped.", fontSize = H3)
                        Spacer(Modifier.height(space))
                        Text("⏰ You win if you match all before time ends!", fontSize = H3)
                    }
                },
                confirmButton = {
                    Button(onClick = { showTutorialState.value = false }) {
                        Text("Got it!")
                    }
                },
            )
        }
    }
}