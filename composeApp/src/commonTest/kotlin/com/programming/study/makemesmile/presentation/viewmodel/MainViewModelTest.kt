package com.programming.study.makemesmile.presentation.viewmodel

import com.programming.study.makemesmile.domain.model.RandomDogImage
import com.programming.study.makemesmile.domain.usecase.GetRandomDogImageUseCase
import com.programming.study.makemesmile.presentation.components.CardFace
import com.programming.study.makemesmile.util.Result
import com.programming.study.makemesmile.util.network.NetworkError
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val getRandomDogImage: GetRandomDogImageUseCase = mock()
    private lateinit var viewModel: MainViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun cardFace_assertDefaultCardFaceFront() {
        viewModel = MainViewModel(getRandomDogImage)

        assertEquals(CardFace.Front, viewModel.cardFace.value)
    }

    @Test
    fun isPickImageButtonEnabled_assertDefaultTrue() {
        viewModel = MainViewModel(getRandomDogImage)

        assertTrue(viewModel.isPickImageButtonEnabled.value)
    }

    @Test
    fun isImageLoaded_assertDefaultTrue() {
        viewModel = MainViewModel(getRandomDogImage)

        assertTrue(viewModel.isImageLoaded.value)
    }

    @Test
    fun imageUrl_assertImageUrlDefault() {
        viewModel = MainViewModel(getRandomDogImage)

        assertEquals(MainViewModel.IMAGE_URL_DEFAULT, viewModel.imageUrl.value)
    }

    @Test
    fun pickRandomImage_verifyGetRandomDogImageCall() = runTest {
        everySuspend { getRandomDogImage() } returns Result.Error(NetworkError.UNKNOWN)

        viewModel = MainViewModel(getRandomDogImage)

        viewModel.pickRandomImage()

        verifySuspend(VerifyMode.exactly(1)) {
            getRandomDogImage()
        }
    }

    @Test
    fun pickRandomImage_onSuccess_assertImageUrl() = runTest {
        val expected = "imageUrl"
        val randomDogImage = RandomDogImage(message = expected, status = "status")

        everySuspend { getRandomDogImage() } returns Result.Success(randomDogImage)

        viewModel = MainViewModel(getRandomDogImage)

        viewModel.pickRandomImage()

        assertEquals(expected, viewModel.imageUrl.value)
    }

    @Test
    fun pickRandomImage_onError_assertNull() = runTest {
        everySuspend { getRandomDogImage() } returns Result.Error(NetworkError.UNKNOWN)

        viewModel = MainViewModel(getRandomDogImage)

        viewModel.pickRandomImage()

        assertNull(viewModel.imageUrl.value)
    }

    @Test
    fun pickRandomImage_withSameImageUrl_assertCardFaceBack() = runTest {
        everySuspend { getRandomDogImage() } returns Result.Error(NetworkError.UNKNOWN)

        viewModel = MainViewModel(getRandomDogImage)

        viewModel.pickRandomImage()
        viewModel.pickRandomImage()

       assertEquals(CardFace.Back, viewModel.cardFace.value)
    }

    @Test
    fun flipCardFront_assertCardFaceFront() = runTest {
        viewModel = MainViewModel(getRandomDogImage)

        viewModel.flipCardFront()

        assertEquals(CardFace.Front, viewModel.cardFace.value)
    }

    @Test
    fun enablePickImageButton_assertIsPickImageButtonEnabledTrue() = runTest {
        viewModel = MainViewModel(getRandomDogImage)

        viewModel.enablePickImageButton()

        assertTrue(viewModel.isPickImageButtonEnabled.value)
    }

    @Test
    fun disablePickImageButton_assertIsPickImageButtonEnabledFalse() = runTest {
        viewModel = MainViewModel(getRandomDogImage)

        viewModel.disablePickImageButton()

        assertFalse(viewModel.isPickImageButtonEnabled.value)
    }

    @Test
    fun onImageLoadSuccess_assertCardFaceBack() = runTest {
        val randomDogImage = RandomDogImage(message = "imageUrl", status = "status")

        everySuspend { getRandomDogImage() } returns Result.Success(randomDogImage)

        viewModel = MainViewModel(getRandomDogImage)

        viewModel.pickRandomImage()
        viewModel.onImageLoadSuccess()

        assertEquals(CardFace.Back, viewModel.cardFace.value)
    }

    @Test
    fun onImageLoadSuccess_assertIsImageLoadedTrue() = runTest {
        val randomDogImage = RandomDogImage(message = "imageUrl", status = "status")

        everySuspend { getRandomDogImage() } returns Result.Success(randomDogImage)

        viewModel = MainViewModel(getRandomDogImage)

        viewModel.pickRandomImage()
        viewModel.onImageLoadSuccess()

        assertTrue(viewModel.isImageLoaded.value)
    }

    @Test
    fun onImageLoadError_assertCardFaceBack() = runTest {
        everySuspend { getRandomDogImage() } returns Result.Error(NetworkError.UNKNOWN)

        viewModel = MainViewModel(getRandomDogImage)

        viewModel.pickRandomImage()
        viewModel.onImageLoadError()

        assertEquals(CardFace.Back, viewModel.cardFace.value)
    }

    @Test
    fun onImageLoadError_assertIsImageLoadedFalse() = runTest {
        everySuspend { getRandomDogImage() } returns Result.Error(NetworkError.UNKNOWN)

        viewModel = MainViewModel(getRandomDogImage)

        viewModel.pickRandomImage()
        viewModel.onImageLoadError()

        assertFalse(viewModel.isImageLoaded.value)
    }
}
