package com.example.recipeandroidapp.ui.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    background(color = Color.LightGray.copy(alpha = alpha))
}


@Composable
fun RecipeCardShimmerEffect(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // LEFT: Image shimmer
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight()
                    .shimmerEffect(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Image,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // RIGHT: Text shimmer
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Title
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(0.7f)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Category
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(0.5f)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Description
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Cooking time row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .width(60.dp)
                                .shimmerEffect()
                        )

                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Tags
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .height(24.dp)
                                .width(60.dp)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardShimmerEffectPreview() {
    MaterialTheme {
        RecipeCardShimmerEffect(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}