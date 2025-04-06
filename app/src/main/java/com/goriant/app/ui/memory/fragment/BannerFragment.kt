package com.goriant.app.ui.memory.fragment

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goriant.app.ui.H1
import com.goriant.app.ui.H2
import com.goriant.app.ui.H2_PADDING
import com.goriant.app.ui.TEXT_COLOR_SECONDARY
import com.goriant.app.ui.memory.MemoryGameViewModel

class BannerFragment {

    @Composable
    fun Display(memoryGameViewModel: MemoryGameViewModel) {
        // Animated bounce effect for title elements
        val animatedOffset = rememberInfiniteTransition(label = "bounce").animateFloat(
            initialValue = 0f,
            targetValue = 10f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        ).value

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Game Banner
            GameBanner(animatedOffset)

            // Game Sub-Banner
            Text(
                text = "🍇 🍩 🍒 🧁 🍍",
                fontSize = 34.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .graphicsLayer { translationY = -animatedOffset }
            )
            // Level and Timer Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Level: ${memoryGameViewModel.level}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TEXT_COLOR_SECONDARY,
                    fontSize = H2,
                    modifier = Modifier.padding(H2_PADDING)
                )
                Text(
                    text = "Time: ${memoryGameViewModel.timerValue} ⏰",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TEXT_COLOR_SECONDARY,
                    fontSize = H2,
                    modifier = Modifier.padding(H2_PADDING)
                )
            }
        }
    }

    @Composable
    private fun GameBanner(animatedOffset: Float) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🍓 Tasty Twins 🍰",
                fontSize = H1,
                style = MaterialTheme.typography.headlineSmall.copy(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFCE3477), Color(0xFFCC0163))
                    ),
                    shadow = Shadow(
                        color = Color.White,
                        offset = Offset(10f, 10f),
                        blurRadius = 8f
                    )
                ),
                modifier = Modifier.graphicsLayer { translationY = animatedOffset }
            )
        }
    }
}