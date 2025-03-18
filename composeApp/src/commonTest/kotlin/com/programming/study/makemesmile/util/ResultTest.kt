package com.programming.study.makemesmile.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ResultTest {

    @Test
    fun onSuccess_withResultSuccess_assertResult() {
        val result: Result<Int, Unit> = Result.Success(1)

        assertEquals(result, result.onSuccess {})
    }

    @Test
    fun onSuccess_withResultSuccess_assertValue() {
        val expected = 1
        val result: Result<Int, Unit> = Result.Success(expected)

        result.onSuccess {
            assertEquals(expected, it)
        }
    }

    @Test
    fun onError_withResultError_assertResult() {
        val result: Result<Int, Unit> = Result.Error(Unit)

        assertEquals(result, result.onError {})
    }

    @Test
    fun onError_withResultError_assertValue() {
        val expected = Unit
        val result: Result<Int, Unit> = Result.Error(expected)

        result.onError {
            assertEquals(expected, it)
        }
    }

    @Test
    fun getValueOrNull_withResultSuccess_assertValue() {
        val expected = 1
        val result: Result<Int, Unit> = Result.Success(expected)

        val actual = result.getValueOrNull()

        assertEquals(expected, actual)
    }

    @Test
    fun getValueOrNull_withResultError_assertNull() {
        val expected = Unit
        val result: Result<Int, Unit> = Result.Error(expected)

        val actual = result.getValueOrNull()

        assertNull(actual)
    }

    @Test
    fun mapSuccess_withResultError_assertSameResultError() {
        val result: Result<Int, Unit> = Result.Error(Unit)

        val actual = result.mapSuccess { 1 }

        assertEquals(result, actual)
    }

    @Test
    fun mapSuccess_withResultSuccess_assertMappedResultSuccess() {
        val result: Result<Int, Unit> = Result.Success(1)

        val actual = result.mapSuccess { it * 2 }

        val expected: Result<Int, Unit> = Result.Success(2)

        assertEquals(expected, actual)
    }

    @Test
    fun mapError_withResultError_assertMappedResultError() {
        val result: Result<Int, Boolean> = Result.Error(true)

        val actual = result.mapError { false }

        val expected: Result<Int, Boolean> = Result.Error(false)

        assertEquals(expected, actual)
    }

    @Test
    fun mapSuccess_withResultSuccess_assertSameResultSuccess() {
        val result: Result<Int, Boolean> = Result.Success(1)

        val actual = result.mapError { false }

        assertEquals(result, actual)
    }
}
