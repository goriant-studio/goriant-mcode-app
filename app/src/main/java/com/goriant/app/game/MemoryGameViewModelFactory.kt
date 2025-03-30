package com.goriant.app.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goriant.app.ui.memory.MemoryGameViewModel

class MemoryGameViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoryGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoryGameViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
