package com.goriant.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.goriant.app.game.SoundManager
import com.goriant.app.style.MemoryGameTheme
import com.goriant.app.ui.memory.screen.MemoryGameScreen


class MainActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
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

    override fun onPause() {
        super.onPause()
        if (SoundManager.isMusicPlaying()) {
            SoundManager.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        // App is foregrounded
        SoundManager.playBackgroundMusic(this)
    }
}
