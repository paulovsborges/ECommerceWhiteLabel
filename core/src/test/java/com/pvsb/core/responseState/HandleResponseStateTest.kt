package com.pvsb.core.responseState

import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.handleResponseState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class HandleResponseStateTest {

    @Test
    fun `should return a complete success state`() = runTest {

        val expectedResult = "hello"
        var showLoading = true
        var emissionCount = 0

        emitSuccessState().collectLatest { state ->
            emissionCount++

            state.handleResponseState<String>(
                onLoading = { loading ->
                    Assert.assertNotNull(loading)
                    Assert.assertEquals(showLoading, loading)
                    showLoading = false
                }, onSuccess = { success ->
                Assert.assertEquals(expectedResult, success)
            }, onError = { exception ->
                Assert.assertNull(exception)
            }
            )

            when (emissionCount) {
                1 -> {
                    Assert.assertTrue(state is ResponseState.Loading)
                }
                2 -> {
                    Assert.assertTrue(state is ResponseState.Complete.Success<*>)
                }
            }
        }
        Assert.assertEquals(2, emissionCount)
    }

    @Test
    fun `should return a fail state`() = runTest {

        val expectedErrorMessage = "Testing bad emissions"
        var showLoading = true
        var count = 0
        val emissionsMap = mutableMapOf<Int, ResponseState>()

        emitErrorState().collectLatest { state ->
            count++

            emissionsMap[count] = state

            state.handleResponseState<String>(
                onLoading = {
                    Assert.assertNotNull(it)
                    Assert.assertEquals(showLoading, it)
                    showLoading = false
                },
                onSuccess = {
                    Assert.assertNull(it)
                },
                onError = {
                    Assert.assertNotNull(it)
                    Assert.assertSame(expectedErrorMessage, it.message)
                    Assert.assertTrue(emissionsMap.values.isNotEmpty())
                    Assert.assertFalse(emissionsMap.values.contains(ResponseState.Complete.Empty))
                    Assert.assertTrue(emissionsMap[1] is ResponseState.Loading)
                    Assert.assertTrue(emissionsMap[2] is ResponseState.Complete.Fail)
                }
            )
        }
    }

    @Test
    fun `when the flow returns a empty state`() = runTest {

        var count = 0

        emitEmptyState().collectLatest { state ->
            count++
            state.handleResponseState<Unit>(
                onLoading = {
                    Assert.assertNotNull(it)
                },
                onSuccess = {},
                onError = {},
                onEmpty = {
                    Assert.assertTrue(state is ResponseState.Complete.Empty)
                    Assert.assertTrue(count == 2)
                }
            )
        }
    }

    private suspend fun emitSuccessState(): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        emit(ResponseState.Complete.Success("hello"))
    }

    private suspend fun emitErrorState(): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        throw Exception("Testing bad emissions")
        emit(ResponseState.Complete.Empty)
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }

    private suspend fun emitEmptyState(): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        emit(ResponseState.Complete.Empty)
    }
}
