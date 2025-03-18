package com.programming.study.makemesmile.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programming.study.makemesmile.domain.usecase.GetRandomDogImageUseCase
import com.programming.study.makemesmile.presentation.components.CardFace
import kotlinx.coroutines.launch

class MainViewModel(
    private val getRandomDogImage: GetRandomDogImageUseCase,
) : ViewModel() {

    private val _cardFace = mutableStateOf(CardFace.Front)
    val cardFace: State<CardFace> = _cardFace

    private val _isPickImageButtonEnabled = mutableStateOf(true)
    val isPickImageButtonEnabled: State<Boolean> = _isPickImageButtonEnabled

    private val _isImageLoaded = mutableStateOf(true)
    val isImageLoaded: State<Boolean> = _isImageLoaded

    private val _imageUrl: MutableState<String?> = mutableStateOf(IMAGE_URL_DEFAULT)
    val imageUrl: State<String?> = _imageUrl

    fun pickRandomImage() {
        viewModelScope.launch {
            val newImageUrl = getRandomDogImage().getValueOrNull()?.message

            if (imageUrl.value == newImageUrl) {
                flipCardBack()
            }
            _imageUrl.value = newImageUrl
        }
    }

    fun flipCardFront() {
        _cardFace.value = CardFace.Front
    }

    private fun flipCardBack() {
        _cardFace.value = CardFace.Back
    }

    fun enablePickImageButton() {
        _isPickImageButtonEnabled.value = true
    }

    fun disablePickImageButton() {
        _isPickImageButtonEnabled.value = false
    }

    fun onImageLoadSuccess() {
        if (isImageUrlNotDefault()) {
            flipCardBack()
            _isImageLoaded.value = true
        }
    }

    fun onImageLoadError() {
        if (isImageUrlNotDefault()) {
            flipCardBack()
            _isImageLoaded.value = false
        }
    }

    private fun isImageUrlNotDefault() = imageUrl.value != IMAGE_URL_DEFAULT

    companion object {
        const val IMAGE_URL_DEFAULT = "IMAGE_URL_DEFAULT"
    }
}
