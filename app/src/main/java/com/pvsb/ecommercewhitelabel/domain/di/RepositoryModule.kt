package com.pvsb.ecommercewhitelabel.domain.di

import com.pvsb.ecommercewhitelabel.domain.repository.HomeRepository
import com.pvsb.ecommercewhitelabel.domain.repository.HomeRepositoryImpl
import com.pvsb.ecommercewhitelabel.domain.repository.CartRepository
import com.pvsb.ecommercewhitelabel.domain.repository.CartRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideHomeImpl(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    fun provideCartImpl(impl: CartRepositoryImpl): CartRepository
}