package com.programming.study.makemesmile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.programming.study.makemesmile.presentation.viewmodel.MainViewModel
import com.programming.study.makemesmile.presentation.components.CardFace
import com.programming.study.makemesmile.presentation.components.CommonButton
import com.programming.study.makemesmile.presentation.components.FlippableCard
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import makemesmile.composeapp.generated.resources.Res
import makemesmile.composeapp.generated.resources.flip_card_button_error_label
import makemesmile.composeapp.generated.resources.flip_card_button_success_label
import makemesmile.composeapp.generated.resources.pick_image_button_label
import makemesmile.composeapp.generated.resources.sad_dog_icon
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    viewModel: MainViewModel = koinViewModel()
) {
    MaterialTheme {
        val imagePainter = rememberAsyncImagePainter(
            model = viewModel.imageUrl.value,
            onSuccess = {
                viewModel.onImageLoadSuccess()
            },
            onError = {
                viewModel.onImageLoadError()
            }
        )

        LayoutContainer {
            FlippableCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                cardFace = viewModel.cardFace.value,
                elevation = 5.dp,
                shape = RoundedCornerShape(8.dp),
                front = { FrontCard() },
                back = {
                    BackCard(
                        isImageLoaded = viewModel.isImageLoaded.value,
                        imagePainter = imagePainter
                    )
                },
            )

            if (viewModel.cardFace.value == CardFace.Front) {
                PickImageButton(isButtonEnabled = viewModel.isPickImageButtonEnabled.value) {
                    viewModel.pickRandomImage()
                    viewModel.disablePickImageButton()
                }
            } else {
                FlipCardButton(viewModel.isImageLoaded.value) {
                    viewModel.flipCardFront()
                    viewModel.enablePickImageButton()
                }
            }
        }
    }
}

@Composable
private fun PickImageButton(
    isButtonEnabled: Boolean,
    onClick: () -> Unit
) {
    CommonButton(
        enabled = isButtonEnabled,
        onClick = onClick
    ) {
        if (isButtonEnabled) {
            Text(text = stringResource(Res.string.pick_image_button_label))
        } else {
            Box(modifier = Modifier.size(16.dp)) {
                CircularProgressIndicator(
                    color = Color(0xFF3BB4FF),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Composable
private fun FlipCardButton(
    isImageLoaded: Boolean,
    onClick: () -> Unit
) {
    CommonButton(onClick = onClick) {
        val text = if (isImageLoaded) {
            stringResource(Res.string.flip_card_button_success_label)
        } else {
            stringResource(Res.string.flip_card_button_error_label)
        }

        Text(text = text)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun FrontCard() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition {
            LottieCompositionSpec.JsonString(
                Res.readBytes("files/corgi_animation.json").decodeToString()
            )
        }

        Image(
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever
            ),
            contentDescription = null
        )
    }
}

@Composable
private fun BackCard(
    isImageLoaded: Boolean,
    imagePainter: AsyncImagePainter
) {
    val painter = if (isImageLoaded) {
        imagePainter
    } else {
        painterResource(Res.drawable.sad_dog_icon)
    }

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painter,
        contentDescription = null
    )
}
