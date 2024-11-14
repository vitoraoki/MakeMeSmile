package com.programming.study.makemesmile.di

import com.programming.study.makemesmile.domain.usecase.GetRandomDogImage
import com.programming.study.makemesmile.domain.usecase.GetRandomDogImageUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetRandomDogImage) bind GetRandomDogImageUseCase::class
}
