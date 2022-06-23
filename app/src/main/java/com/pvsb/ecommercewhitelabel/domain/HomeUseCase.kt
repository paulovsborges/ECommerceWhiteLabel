package com.pvsb.ecommercewhitelabel.domain

import com.pvsb.ecommercewhitelabel.data.model.ProductDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor() {

    suspend fun mockData(): Flow<List<ProductDTO>> = flow {

        val mockedData = listOf(
            ProductDTO("Apple airpods oLiginal", "R$59.99"),
            ProductDTO("Mouse logitech mx ", "R$459.99"),
            ProductDTO("Galaxy buds", "R$659.99"),
            ProductDTO("Teclado corsair K55", "R$249.99")
        )

        emit(mockedData)
    }
}