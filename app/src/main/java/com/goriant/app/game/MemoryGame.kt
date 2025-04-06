package com.goriant.app.game

import com.goriant.app.model.MemoryCard

class MemoryGame(
    private val level: Int,
    private val images: List<Int>
) {
    fun createCards(): List<MemoryCard> {
        val numberOfPairs = when (level) {
            1 -> 8
            2,3 -> 8
            else -> 12
        }

        val selectedImages = images.shuffled().take(numberOfPairs)

        val cardImages = (selectedImages + selectedImages).shuffled()

        return cardImages.mapIndexed { index, resId ->
            MemoryCard(
                id = index,
                imageResId = resId
            )
        }
    }
}