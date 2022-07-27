package com.pvsb.ecommercewhitelabel.usecase

import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.ProductFilterCategories
import com.pvsb.core.model.ProductFilters
import com.pvsb.core.model.ProductFiltersPrice
import com.pvsb.core.utils.MockFactory.Products.productsList
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.handleResponseState
import com.pvsb.ecommercewhitelabel.data.repository.HomeRepository
import com.pvsb.ecommercewhitelabel.domain.usecase.FiltersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FiltersUseCaseTest {

    private val repository: HomeRepository = mockk()
    private lateinit var useCase: FiltersUseCase

    @Before
    fun setUp() {
        useCase = FiltersUseCase(repository)
    }

    @Test
    fun `should return a empty filtered list on the initial request`() = runTest {
        val product = ProductDTO()
        val res = listOf(product)
        coEvery { repository.getProducts() } returns res
        val result = useCase.getProducts()

        result
            .collectLatest { state ->
                state.handleResponseState<List<ProductDTO>>(
                    onLoading = { },
                    onSuccess = {
                        Assert.assertTrue(state is ResponseState.Complete.Success<*>)
                        Assert.assertSame(res.first(), it.first())
                    },
                    onError = {
                        Assert.assertNull(it)
                    }
                )
            }
    }

    @Test
    fun `when it should return search by query`() = runTest {

        val filters = ProductFilters(
            price = ProductFiltersPrice(
                maxValue = 0.0,
                minValue = 0.0
            ),
            categories = emptyList()
        )

        val query = "gtx"
        val res = productsList
        val expected = listOf(
            ProductDTO(
                1,
                "Nvidia GTX 1050",
                price = 1499.99,
                categoryId = 1,
            ),
            ProductDTO(
                2,
                "Nvidia GTX 3050",
                price = 2129.90,
                categoryId = 1
            )
        )

        coEvery { repository.getProducts() } returns res
        val result = useCase.doSearch(query, filters)

        result.collect { state ->
            state.handleResponseState<List<ProductDTO>>(
                onSuccess = {
                    Assert.assertTrue(2 == it.size)
                    Assert.assertTrue(!it.contains(res[3]))
                    Assert.assertEquals(expected, it)
                }, onError = { }, onLoading = {})
        }
    }

    @Test
    fun `filter products by categories`() = runTest {
        val filters = ProductFilters(
            price = ProductFiltersPrice(
                maxValue = 0.0,
                minValue = 0.0
            ),
            categories = listOf(
                ProductFilterCategories(
                    id = 1,
                    name = "Hardware"
                )
            )
        )

        val query = ""
        val res = productsList
        val expected = res.filter { it.categoryId == 1 }

        coEvery { repository.getProducts() } returns res
        val result = useCase.doSearch(query, filters)

        result.collect { state ->
            state.handleResponseState<List<ProductDTO>>(
                onSuccess = {
                    Assert.assertTrue(7 == it.size)
                    Assert.assertTrue(!it.contains(res[4]))
                    Assert.assertTrue(expected == it)
                }, onError = {}, onLoading = {})
        }
    }

    @Test
    fun `filter products by price`() = runTest {
        val filters = ProductFilters(
            price = ProductFiltersPrice(
                maxValue = 2000.00,
                minValue = 1000.00
            ),
            categories = emptyList()
        )

        val query = ""
        val res = productsList
        coEvery { repository.getProducts() } returns res
        val result = useCase.doSearch(query, filters)

        result.collect { state ->
            state.handleResponseState<List<ProductDTO>>(
                onSuccess = {
                    it.forEach { product ->
                        Assert.assertTrue(
                            product.price in filters.price.minValue..filters.price.maxValue
                        )
                    }
                    Assert.assertTrue(2 == it.size)
                },
                onError = {}, onLoading = {})
        }
    }

    @Test
    fun `filter products by maximum price`() = runTest {
        val filters = ProductFilters(
            price = ProductFiltersPrice(
                maxValue = 2000.00,
                minValue = 0.0
            ),
            categories = emptyList()
        )

        val query = ""
        val res = productsList
        coEvery { repository.getProducts() } returns res
        val result = useCase.doSearch(query, filters)

        result.collect { state ->
            state.handleResponseState<List<ProductDTO>>(
                onSuccess = {
                    it.forEach { product ->
                        Assert.assertTrue(product.price < filters.price.maxValue)
                    }
                },
                onError = {}, onLoading = { })
        }
    }

    @Test
    fun `filter products by minimum price`() = runTest {
        val filters = ProductFilters(
            price = ProductFiltersPrice(
                maxValue = 0.00,
                minValue = 1000.0
            ),
            categories = emptyList()
        )

        val query = ""
        val res = productsList
        coEvery { repository.getProducts() } returns res
        val result = useCase.doSearch(query, filters)

        result.collect { state ->
            state.handleResponseState<List<ProductDTO>>(
                onSuccess = {
                    it.forEach { product ->
                        Assert.assertTrue(product.price > filters.price.minValue)
                    }
                },
                onError = {}, onLoading = { })
        }
    }

    @Test
    fun `filter products by query, price and category`() = runTest {
        val filters = ProductFilters(
            price = ProductFiltersPrice(
                maxValue = 400.00,
                minValue = 200.00
            ),
            categories = listOf(
                ProductFilterCategories(
                    id = 1,
                    name = "Hardware"
                )
            )
        )

        val query = "corsair"
        val res = productsList
        coEvery { repository.getProducts() } returns res
        val result = useCase.doSearch(query, filters)

        result.collect { state ->
            state.handleResponseState<List<ProductDTO>>(
                onSuccess = {
                    Assert.assertTrue(1 == it.size)
                    Assert.assertTrue(res[6] == it.first())
                },
                onError = {}, onLoading = { })
        }
    }
}