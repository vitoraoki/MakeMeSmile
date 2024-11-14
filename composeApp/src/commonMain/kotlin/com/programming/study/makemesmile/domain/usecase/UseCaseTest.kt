package com.programming.study.makemesmile.domain.usecase

enum class UseCaseTestTypes {
    USECASE_A,
    USECASE_B
}

interface UseCaseTest {
    operator fun invoke(): String
}

class UseCaseA: UseCaseTest {
    override fun invoke(): String = "Use Case A"
}

class UseCaseB: UseCaseTest {
    override fun invoke(): String = "Use Case B"
}