package com.goriant.app.game

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import com.goriant.app.R

object SoundManager {

    private var soundPool: SoundPool? = null
    private var backgroundPlayer: MediaPlayer? = null
    private val soundMap = mutableMapOf<String, Int>()

    fun init(context: Context) {
        soundPool = SoundPool.Builder().setMaxStreams(5).build()
        loadSounds(context)
    }

    private fun loadSounds(context: Context) {
        // sounds effect
        soundMap["match_pair"] = soundPool?.load(context, R.raw.match_pair, 1) ?: 0
        soundMap["select"] = soundPool?.load(context, R.raw.select, 1) ?: 0

        // background music
        soundMap["intro_show"] = soundPool?.load(context, R.raw.intro_show, 1) ?: 0
        soundMap["background_music"] = soundPool?.load(context, R.raw.background_music, 1) ?: 0
    }

    fun playSound(name: String) {
        val soundId = soundMap[name]
        soundId?.let {
            soundPool?.play(it, 1f, 1f, 1, 0, 1f)
        }
    }

    fun playBackgroundMusic(context: Context) {
        if (backgroundPlayer == null) {
            backgroundPlayer = MediaPlayer.create(context.applicationContext, R.raw.background_music)
            backgroundPlayer?.isLooping = true
            backgroundPlayer?.setVolume(0.5f, 0.5f)
        }
        backgroundPlayer?.start()
    }

    fun stopBackgroundMusic() {
        backgroundPlayer?.pause()
    }

    fun isMusicPlaying(): Boolean {
        return backgroundPlayer?.isPlaying == true
    }

    fun release() {
        soundPool?.release()
        soundPool = null
        backgroundPlayer?.release()
        backgroundPlayer = null
    }

    fun pause() {
        backgroundPlayer?.pause()
    }

    fun resume() {
        backgroundPlayer?.start()
    }
}