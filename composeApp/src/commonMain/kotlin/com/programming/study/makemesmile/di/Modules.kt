package com.programming.study.makemesmile.di

import com.programming.study.makemesmile.util.network.CustomHttpClient
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val commonModule = module {
    includes(dataModule, domainModule, presentationModule)

    factory<HttpClient> {
        CustomHttpClient(engine = get()).build()
    }
}
