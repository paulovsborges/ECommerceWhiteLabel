package com.pvsb.ecommercewhitelabel.usecase

import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.data.repository.CartRepository
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CartUseCaseTest {

    private val repository: CartRepository = mockk()
    private lateinit var useCase: CartUseCase

    @Before
    fun setUp() {
        useCase = CartUseCase(repository)
    }

    @Test
    fun `when init cart is success`() = runTest {

        val cart = mockk<PopulateCartDTO>()
        val cartId = "1"
        var emissionsCount = 0

        coEvery { repository.createCart(cartId, cart) } returns true

        val result = useCase.createCart(cartId, cart)

        result.collectLatest { state ->
            emissionsCount++

            when (emissionsCount) {
                1 -> {
                    Assert.assertTrue(state is ResponseState.Loading)
                }
                2 -> {
                    Assert.assertTrue(state is ResponseState.Complete.Success<*>)
                }
            }
        }

        Assert.assertTrue(emissionsCount == 2)

        coVerify { repository.createCart(cartId, cart) }
    }

    @Test
    fun `when init cart is fail`() = runTest {
        val cart = mockk<PopulateCartDTO>()
        val cartId = "1"
        var emissionsCount = 0

        coEvery { repository.createCart(cartId, cart) } returns false
        val result = useCase.createCart(cartId, cart)

        result.collectLatest { state ->
            emissionsCount++

            when (emissionsCount) {
                1 -> {
                    Assert.assertTrue(state is ResponseState.Loading)
                }
                2 -> {
                    Assert.assertTrue(state is ResponseState.Complete.Fail)
                }
            }
        }

        Assert.assertTrue(emissionsCount < 3)
    }
}