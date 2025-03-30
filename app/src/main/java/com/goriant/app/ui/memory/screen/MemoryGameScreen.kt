package com.goriant.app.ui.memory.screen

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goriant.app.game.MemoryGameViewModelFactory
import com.goriant.app.model.MemoryCard
import com.goriant.app.ui.memory.MemoryCardView
import com.goriant.app.ui.memory.MemoryGameViewModel

class MemoryGameScreen {

    /**
     * Returns the number of pairs for the specified level,
     * using the grid dimensions from createGridForLevel.
     */
    fun numberOfPairsForLevel(level: Int): Int {
        val (rows, columns) = createGridForLevel(level)
        return (rows * columns) / 2
    }

    @Composable
    fun Screen() {

        val app = LocalContext.current.applicationContext as Application
        val memoryGameViewModel: MemoryGameViewModel = viewModel(
            factory = MemoryGameViewModelFactory(app)
        )

        val cards = memoryGameViewModel.memoryCards
        val timerValue = memoryGameViewModel.timerValue
        val level = memoryGameViewModel.level

        val (rows, columns) = createGridForLevel(level)
        val numberOfPairs = numberOfPairsForLevel(level)


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                // Top row: level and timer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Level: $level",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Time: $timerValue",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // cards render here
                BalancedGridScreen(cards, memoryGameViewModel, rows, columns)
            }
        }
    }

    @SuppressLint("UnusedBoxWithConstraintsScope")
    @Composable
    fun BalancedGridScreen(
        items: List<MemoryCard>,
        memoryGameViewModel: MemoryGameViewModel,
        rows: Int,
        columns: Int
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            // Total available width in Dp
            val screenWidth = maxWidth

            // Desired minimum cell width
            val cellMinWidth = 150.dp

            // Calculate how many columns can fit based on the available width
            val possibleColumns = (screenWidth / cellMinWidth).toInt()

            // Build the grid using a fixed column count
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(items.size) { index ->
                    // Each item is placed inside a Box that is square
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        val card = items[index]
                        MemoryCardView().InitView(
                            card = card,
                            onClick = { memoryGameViewModel.flipCard(card) }
                        )
                    }
                }
            }
        }
    }

    fun createGridForLevel(level: Int): Pair<Int, Int> {
        return when (level) {
            1 -> 4 to 4  // 16 cells => 8 pairs
            2, 3 -> 4 to 6  // 24 cells => 12 pairs
            else -> 6 to 8  // 48 cells => 24 pairs
        }
    }
}