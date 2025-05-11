package com.goriant.app.ui.memory

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.goriant.app.BuildConfig
import com.goriant.app.R
import com.goriant.app.model.MemoryCard
import com.goriant.app.style.CARD_SIZE

class MemoryCardView {

    @SuppressLint("UnusedBoxWithConstraintsScope")
    @Composable
    fun InitView(
        card: MemoryCard,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {

        val isDevMode = BuildConfig.DEV
        Log.i("TAG", "isDevMode $isDevMode")
        val forceFaceUp = isDevMode && !card.isMatched.value

        val rotation by animateFloatAsState(
            targetValue = if (forceFaceUp || card.isFaceUp.value || card.isMatched.value) 180f else 0f,
            animationSpec = tween(durationMillis = 300)
        )


        // Animate scale for a little effect
        val scale by animateFloatAsState(
            targetValue = if (card.isFaceUp.value || card.isMatched.value) 1.0f else 0.95f,
            animationSpec = tween(durationMillis = 300)
        )

        // A bright color for face-up background
        val faceUpColor = MaterialTheme.colorScheme.surface

        val gradientColor = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFE0B2), // Light orange
                Color(0xFFFFCC80), // Slightly darker orange
                Color(0xFFFFB74D), // Even darker
            )
        )

        BoxWithConstraints(
            modifier = modifier
                .padding(6.dp)
                .size(CARD_SIZE)
                .clickable { onClick() }
        ) {
            val iconSize: Dp = maxWidth * 0.8f // 80% of the card’s width
            Card(
                modifier = Modifier
                    .rotate(rotation)
                    .scale(scale),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (rotation > 90f) {
                        // Card is face up
                        FaceUpSide(
                            iconSize,
                            faceUpColor = faceUpColor,
                            imageResId = card.imageResId,
                            selectedIndex = if (BuildConfig.DEV) card.selectedIndex.value else null,
                            isMatched = card.isMatched.value
                        )
                    } else {
                        // Card is face down
                        FaceDownSide(
                            faceDownBrush = gradientColor
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun FaceUpSide(iconSize: Dp,
                   faceUpColor: Color,
                   imageResId: Int,
                   selectedIndex: Int?,
                   isMatched: Boolean) {
        val debugBorderColor = Color(0xFFFFA000)

        val borderColor = when {
            isMatched -> Color.Green // Green border for matched cards
            selectedIndex == 0 -> Color.Red
            selectedIndex == 1 -> Color.Blue
            else -> Color(0xFFFFA000) // Default dark yellow
        }

        // Another layer for the background color
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(4.dp, borderColor),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = imageResId),
                contentDescription = "Card Image",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(iconSize)
            )
        }
    }

    @Composable
    fun FaceDownSide(faceDownBrush: Brush) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.facedown_a),
                contentDescription = "Card Back",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}