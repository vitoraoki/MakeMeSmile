package com.programming.study.makemesmile.di

import com.programming.study.makemesmile.data.datasource.MakeMeSmileDataSource
import com.programming.study.makemesmile.data.datasource.MakeMeSmileRemoteDataSource
import com.programming.study.makemesmile.data.repository.MakeMeSmileDefaultRepository
import com.programming.study.makemesmile.data.repository.MakeMeSmileRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::MakeMeSmileRemoteDataSource) bind MakeMeSmileDataSource::class
    singleOf(::MakeMeSmileDefaultRepository) bind MakeMeSmileRepository::class
}
