package com.programming.study.makemesmile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programming.study.makemesmile.domain.usecase.GetRandomDogImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TestViewModel(
    private val getRandomDogImage: GetRandomDogImageUseCase,
) : ViewModel() {
    val text: MutableStateFlow<String> = MutableStateFlow("")

    init {
        getHelloWorld()
    }

    private fun getHelloWorld() {
        viewModelScope.launch {
            val response = getRandomDogImage()
            response.onSuccess {
                text.value = "Text: ${it.message}"
            }
                .onError {
                    text.value = "Text: ${it.name}"
                }
        }
    }
}
