package com.programming.study.makemesmile.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class CardFace(val angle: Float) {
    Front(180f),
    Back(0f);
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlippableCard(
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    elevation: Dp = 1.dp,
    cardFace: CardFace,
    onClick: (CardFace) -> Unit = {},
    front: @Composable () -> Unit = {},
    back: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing,
        )
    )

    Card(
        onClick = { onClick(cardFace) },
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 10f * density
            },
        border = border,
        shape = shape,
        elevation = elevation
    ) {
        if (rotation.value <= 90f) {
            Column(Modifier.fillMaxSize()) {
                back()
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
                    }
            ) {
                front()
            }
        }
    }
}
