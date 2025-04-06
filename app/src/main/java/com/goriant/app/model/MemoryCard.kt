package com.goriant.app.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Represents a single card in the memory game.
 */
data class MemoryCard (
    val id: Int,
    val imageResId: Int,
    val isFaceUp: MutableState<Boolean> = mutableStateOf(false),
    val isMatched: MutableState<Boolean> = mutableStateOf(false),
)
