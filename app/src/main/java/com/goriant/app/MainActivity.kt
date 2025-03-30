package com.goriant.app

import android.app.Application
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.ui.AppBarConfiguration
import com.goriant.app.databinding.ActivityMainBinding
import com.goriant.app.game.MemoryGameViewModelFactory
import com.goriant.app.model.MemoryCard
import com.goriant.app.ui.MemoryGameTheme
import com.goriant.app.ui.memory.MemoryGameViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryGameTheme {
                MemoryGameScreen()
            }
        }
    }

    @Composable
    fun BalancedGridScreen(
        items: List<MemoryCard>,
        memoryGameViewModel: MemoryGameViewModel,
        rows: Int,
        columns: Int
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
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
                        MemoryCardView(
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

    /**
     * Returns the number of pairs for the specified level,
     * using the grid dimensions from createGridForLevel.
     */
    fun numberOfPairsForLevel(level: Int): Int {
        val (rows, columns) = createGridForLevel(level)
        return (rows * columns) / 2
    }

    @Composable
    fun MemoryGameScreen() {

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







//    fun gridDimensionsForLevel(level: Int): Pair<Int, Int> {
//        return when (level) {
//            1 -> 3 to 3
//            2 -> 4 to 4
//            3 -> 5 to 5
//            else -> 6 to 6
//        }
//    }
//
//
//    @Composable
//    fun MemoryGameScreen() {
//
//        val app = LocalContext.current.applicationContext as Application
//        val memoryGameViewModel: MemoryGameViewModel = viewModel(
//            factory = MemoryGameViewModelFactory(app)
//        )
//
//        val cards = memoryGameViewModel.memoryCards
//        val timerValue = memoryGameViewModel.timerValue
//        val level = memoryGameViewModel.level
//
//        val (rows, columns) = gridDimensionsForLevel(level)
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//            ) {
//                // Top row: level and timer
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = "Level: $level",
//                        style = MaterialTheme.typography.headlineSmall
//                    )
//                    Text(
//                        text = "Time: $timerValue",
//                        style = MaterialTheme.typography.headlineSmall
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // This BoxWithConstraints measures the remaining space
//                BoxWithConstraints(
//                    modifier = Modifier
//                        .weight(1f) // fill the rest of the screen
//                ) {
//                    // The available width/height
//                    val screenWidth = maxWidth
//                    val screenHeight = maxHeight
//                    // Take the smaller dimension so it's a square if possible
//                    val minSide = min(screenWidth, screenHeight)
//                    // Use 80% of that dimension for the grid
//                    val gridSize = minSide * 0.8f
//                    // Each card’s size is the grid dimension / number of columns
//                    val cardSize = gridSize / columns
//
//                    // Center the entire grid inside this Box
//                    Box(
//                        modifier = Modifier
//                            .size(gridSize) // The grid area
//                            .align(Alignment.Center)
//                    ) {
//                        // Build rows x columns manually
//                        Column {
//                            for (r in 0 until rows) {
//                                Row {
//                                    for (c in 0 until columns) {
//                                        val index = r * columns + c
//                                        if (index < cards.size) {
//                                            val card = cards[index]
//                                            // Place each card in a Box with the computed size
//                                            MemoryCardView(
//                                                card = card,
//                                                onClick = { memoryGameViewModel.flipCard(card) },
//                                                modifier = Modifier.size(cardSize)
//                                            )
//                                        } else {
//                                            // If you have fewer cards than rows*columns,
//                                            // you can put an empty Box or skip
//                                            Box(modifier = Modifier.size(cardSize))
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }






    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFE0B2), // Light orange
            Color(0xFFFFCC80), // Slightly darker orange
            Color(0xFFFFB74D), // Even darker
        )
    )

    @Composable
    fun MemoryCardView(
        card: MemoryCard,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        // Animate flipping
        val rotation by animateFloatAsState(
            targetValue = if (card.isFaceUp.value || card.isMatched.value) 180f else 0f,
            animationSpec = tween(durationMillis = 300)
        )

        // Animate scale for a little effect
        val scale by animateFloatAsState(
            targetValue = if (card.isFaceUp.value || card.isMatched.value) 1.0f else 0.95f,
            animationSpec = tween(durationMillis = 300)
        )

        // A casual pastel color for face-down
        val faceDownColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
        // A bright color for face-up background
        val faceUpColor = MaterialTheme.colorScheme.surface

//        new
        BoxWithConstraints(
            modifier = modifier
                .padding(6.dp)
                .aspectRatio(1f)
                .clickable { onClick() }
        ) {
            val iconSize: Dp = maxWidth * 0.5f // 50% of the card’s width
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation)
                    .scale(scale),
                shape = RoundedCornerShape(16.dp),  // bigger rounded corners
                elevation = CardDefaults.cardElevation(8.dp), // add shadow
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ) {
                // We can use a Box to create a gradient background
                val gradient = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gradient),
                    contentAlignment = Alignment.Center
                ) {
                    if (rotation > 90f) {
                        // Card is face up
                        FaceUpSide(
                            iconSize,
                            faceUpColor = faceUpColor,
                            imageResId = card.imageResId
                        )
                    } else {
                        // Card is face down
                        FaceDownSide(
                            faceDownColor = faceDownColor
                        )
                    }
                }
            }
        }
    }

//        new


//        Box(
//            modifier = Modifier
//                .padding(4.dp)
//                .aspectRatio(0.8f)
//                .clickable(
//                    enabled = !card.isMatched.value && !card.isFaceUp.value
//                ) { onClick() },
//            contentAlignment = Alignment.Center
//        ) {
//            Card(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .rotate(rotation)
//                    .scale(scale),
//                shape = RoundedCornerShape(16.dp),  // bigger rounded corners
//                elevation = CardDefaults.cardElevation(8.dp), // add shadow
//                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
//            ) {
//                // We can use a Box to create a gradient background
//                val gradient = Brush.verticalGradient(
//                    colors = listOf(
//                        MaterialTheme.colorScheme.background,
//                        MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
//                    )
//                )
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(gradient),
//                    contentAlignment = Alignment.Center
//                ) {
//                    if (rotation > 90f) {
//                        // Card is face up
//                        FaceUpSide(
//                            faceUpColor = faceUpColor,
//                            imageResId = card.imageResId
//                        )
//                    } else {
//                        // Card is face down
//                        FaceDownSide(
//                            faceDownColor = faceDownColor
//                        )
//                    }
//                }
//            }
//        }
}

@Composable
fun FaceUpSide(iconSize: Dp, faceUpColor: Color, imageResId: Int) {
    // Another layer for the background color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(faceUpColor)
            .padding(12.dp), // Slight padding inside the card
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = imageResId),
            contentDescription = "Card Image",
            tint = Color.Unspecified,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun FaceDownSide(faceDownColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(faceDownColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "?",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

