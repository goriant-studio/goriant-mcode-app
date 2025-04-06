package com.goriant.app.ui.memory.screen

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goriant.app.R
import com.goriant.app.game.MemoryGameViewModelFactory
import com.goriant.app.model.MemoryCard
import com.goriant.app.ui.memory.MemoryCardView
import com.goriant.app.ui.memory.MemoryGameViewModel
import com.goriant.app.ui.memory.fragment.BannerFragment
import com.goriant.app.ui.memory.fragment.FooterFragment
import com.goriant.app.ui.memory.fragment.GameLevelCompleteDialog
import com.goriant.app.ui.memory.fragment.GameTutorialDialog

class MemoryGameScreen {

    @Composable
    fun MainScreen() {
        val showTutorialState = remember { mutableStateOf(true) }

        GameTutorialDialog().Show(showTutorialState)

        val app = LocalContext.current.applicationContext as Application
        val memoryGameViewModel: MemoryGameViewModel = viewModel(
            factory = MemoryGameViewModelFactory(app)
        )

        // Start the timer when the screen is first displayed
        LaunchedEffect(key1 = Unit) {
            memoryGameViewModel.startTimer()
        }

        // If a level complete event is triggered, show the dialog
        if (memoryGameViewModel.levelCompleteEvent) {
            GameLevelCompleteDialog().Show(
                onDismiss = { memoryGameViewModel.resetLevelCompleteEvent() },
                onNextLevel = { memoryGameViewModel.prepareNextLevel() }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Game Background",
                modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.6f),
                contentScale = ContentScale.Crop // Ensures the image covers the entire background
            )
            Scaffold(
                topBar = {
                    BannerFragment().Display(memoryGameViewModel)
                },
                containerColor = Color.Transparent,
                bottomBar = {
                    FooterFragment().Display(
                        onHomeClick = { /* Handle home click */ },
                        onRestartClick = { memoryGameViewModel.startNewGame() },
                        onSettingsClick = { /* Handle settings click */ },
                        onHelpClick = { showTutorialState.value = true },
                    )
                }
            ) { innerPadding ->
                MainContent(
                    memoryGameViewModel = memoryGameViewModel,
                    modifier = Modifier
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding()*1.1f
                        )
                )
            }
        }
    }

    @SuppressLint("UnusedBoxWithConstraintsScope")
    @Composable
    fun MainContent(memoryGameViewModel: MemoryGameViewModel, modifier: Modifier) {

        val cards = memoryGameViewModel.memoryCards
        val level = memoryGameViewModel.level

        val (rows, columns) = createGridForLevel(level)
        val numberOfPairs = numberOfPairsForLevel(level)

        Column(modifier = modifier.fillMaxSize().wrapContentHeight()) {

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                // Total available width in Dp
                val screenWidth = maxWidth * 0.9f // 90%

                // Desired minimum cell width
                val cellMinWidth = 120.dp

                // Calculate how many columns can fit based on the available width
                val possibleColumns = (screenWidth / cellMinWidth).toInt()

                // Build the grid using a fixed column count
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier.wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(cards.size) { index ->
                        // Each item is placed inside a Box that is square
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            val card = cards[index]
                            MemoryCardView().InitView(
                                memoryGameViewModel,
                                card,
                                onClick = { memoryGameViewModel.flipCard(card) }
                            )
                        }
                    }
                }
            }
        }
    }

    fun createGridForLevel(level: Int): Pair<Int, Int> {
        return when (level) {
            1 -> 4 to 4  // 16 cells => 8 pairs
            2, 3 -> 4 to 4  // 24 cells => 12 pairs
            else -> 4 to 4  // 48 cells => 24 pairs
        }
    }

    fun numberOfPairsForLevel(level: Int): Int {
        val (rows, columns) = createGridForLevel(level)
        return (rows * columns) / 2
    }
}