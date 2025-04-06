package com.goriant.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.goriant.app.game.SoundManager
import com.goriant.app.ui.MemoryGameTheme
import com.goriant.app.ui.memory.screen.MemoryGameScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SoundManager.init(this)
        SoundManager.playBackgroundMusic(this)

        setContent {
            MemoryGameTheme {
                MemoryGameScreen().MainScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.release()
    }
}
