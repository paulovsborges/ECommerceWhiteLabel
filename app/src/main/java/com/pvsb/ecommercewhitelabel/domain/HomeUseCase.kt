package com.pvsb.ecommercewhitelabel.domain

import com.pvsb.ecommercewhitelabel.data.model.MainHomeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor() {

    suspend fun mockData(): Flow<List<MainHomeModel>> = flow {

        val mockedData = listOf(
            MainHomeModel("Apple airpods oLiginal", "R$59.99"),
            MainHomeModel("Mouse logitech mx ", "R$459.99"),
            MainHomeModel("Galaxy buds", "R$659.99"),
            MainHomeModel("Teclado corsair K55", "R$249.99")
        )

        emit(mockedData)
    }
}