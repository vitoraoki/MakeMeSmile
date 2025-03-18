# MakeMeSmile
What can make us happier than an image of a dog, right? With the idea of showing users a random image of a dog to brighten their day, `Make Me Smile` is an Android and iOS app developed as a study case for Kotlin and Compose Multiplatform. The app covers concepts such as HTTP requests, full layouts and animations in Compose, mobile architectures using Clean Architecture and MVVM, and unit testing. The API used to fetch the data was [Dog Api](https://dog.ceo/dog-api/) (a big thanks to the developers for creating such a great API).

## Dependencies
The folllowing depencendies was used to develop the app:
- [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/)
- [Lifecycle components](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-lifecycle.html)
- [Koin (dependency injection)](https://insert-koin.io)
- [Ktor (HTTP requests)](https://ktor.io)
- [Compottie (animations)](https://github.com/alexzhirkevich/compottie)
- [Coil (image loading library)](https://coil-kt.github.io/coil/)
- [Kotlin test](https://kotlinlang.org/api/core/kotlin-test/)
- [Coroutines test](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/)
- [Mokkery](https://mokkery.dev)

## Architecture
Designed for scalability, the app follows a `Clean Architecture`, structured into three layers: `data`, `domain`, and `presentation`. This approach ensures a clear separation of concerns, making each layer independent, maintainable, and easily replaceable if needed.

For the presentation layer, the `MVVM pattern` was implemented, keeping the view independent of the business logic while managing its state through the `ViewModel`. In this project, we leveraged the power of [Android ViewModels](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-viewmodel.html) provided by the Lifecycle components.

## Dependency injection
The app follows the `Dependency Injection pattern`, which aligns with the `Dependency Inversion Principle`, making it more flexible, maintainable, and testable. To achieve this, the project utilizes the `Koin` library.

## Layout and Animations
The layout was entirely built using `Compose Multiplatform`. Its powerful API enabled the creation of a fluid and consistent design across both platforms, allowing for smooth animations—such as the card flip effect used to display the dog's image.

The animation on the front side of the card was implemented using the `Compottie` library, a KMP library that renders [Lottie Files](https://lottiefiles.com). Additionally, dog images were loaded efficiently using the `Coil` library.

## App Evidences
Now, the most important part—the videos showcasing the app running on both Android and iOS. (Please note that the video quality may not be optimal, as it was recorded using Android and Android emulators.)

| Android Success | iOS Success |
|---|---|
| <video src='https://github.com/user-attachments/assets/796b0e97-df7a-4537-a9da-48cbc4b7002d' width=200/> | <video src='https://github.com/user-attachments/assets/0def205a-e19c-4cd3-a6ed-a89265e6b667' width=200/> |

| Android Error | iOS Error |
|---|---|
| <video src='https://github.com/user-attachments/assets/0103d5ba-1b29-4d5f-8bb2-7a379ef7270b' width=200/> | <video src='https://github.com/user-attachments/assets/aae46ad6-d770-4da6-9f7d-ce46c7e0b52e' width=200/> |
