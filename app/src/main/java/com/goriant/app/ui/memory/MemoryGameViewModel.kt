package com.goriant.app.ui.memory

import android.app.Application
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goriant.app.databinding.FragmentMemoryBinding
import com.goriant.app.game.MemoryGame
import com.goriant.app.game.SoundManager
import com.goriant.app.model.MemoryCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MemoryGameViewModel(application: Application) : ViewModel() {

    private var _binding: FragmentMemoryBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Current level
    var level by mutableStateOf(1)
        private set

    // Timer (in seconds)
    var timerValue by mutableStateOf(30)
        private set

    // The cards currently displayed
    var memoryCards: List<MemoryCard> by mutableStateOf(emptyList())
        private set

    // Track the first flipped card
    private var firstFlippedMemoryCard: MemoryCard? = null

    // For demonstration, just use a small set of placeholder image resource IDs

    private var countDownTimer: CountDownTimer? = null

    private var isFlippingInProgress = false

    // Initialize list of images dynamically using file names
    // Use the application context (from AndroidViewModel)
    private var allImages: List<Int> = MemoFragment.FoodDrawablesGenerator.generateFoodDrawables(
        application, 100
    )

    init {
        startNewGame()
    }

    fun startNewGame() {
        // Stop any existing timer
        countDownTimer?.cancel()

        // Create new MemoryGame
        val memoryGame = MemoryGame(level, allImages)
        memoryCards = memoryGame.createCards()

        // Reset the timer value based on level
        timerValue = when (level) {
            1 -> 30
            2 -> 45
            3 -> 60
            else -> 75
        }

        // Start the timer
        countDownTimer = object : CountDownTimer(timerValue * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                timerValue = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                // Time is up! We could handle "game over" logic here.
                timerValue = 0
            }
        }.start()
    }

    fun flipCard(memoryCard: MemoryCard) {
        SoundManager.playSound("select")
        // If card is already matched, face up, or flipping is in progress, do nothing
        if (memoryCard.isMatched.value || memoryCard.isFaceUp.value || isFlippingInProgress) return

        // Flip the card
        memoryCard.isFaceUp.value = true

        if (firstFlippedMemoryCard == null) {
            // First card in the pair
            firstFlippedMemoryCard = memoryCard
        } else {
            // Second card
            val secondFlippedCard = memoryCard

            if (firstFlippedMemoryCard?.imageResId == secondFlippedCard.imageResId) {
                // It's a match
                firstFlippedMemoryCard?.isMatched?.value = true
                secondFlippedCard.isMatched.value = true
                firstFlippedMemoryCard = null
                checkIfAllMatched()
                // event match
                SoundManager.playSound("match_pair")
            } else {
                // Not a match -> flip both back down after a short delay
                val oldFirstCard = firstFlippedMemoryCard
                firstFlippedMemoryCard = null

                // Prevent more flips while we wait
                isFlippingInProgress = true

                viewModelScope.launch {
                    // Delay 300ms so user can briefly see the second card
                    delay(300)

                    oldFirstCard?.isFaceUp?.value = false
                    secondFlippedCard.isFaceUp.value = false

                    // Allow flips again
                    isFlippingInProgress = false
                }
            }
        }


    }

    private fun checkIfAllMatched() {
        if (memoryCards.all { it.isMatched.value }) {
            // All cards matched. Increase level, start new game
            level++
            startNewGame()
        }
    }
}
