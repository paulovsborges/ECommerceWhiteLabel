package com.pvsb.ecommercewhitelabel.data.di

import com.pvsb.ecommercewhitelabel.data.repository.HomeRepository
import com.pvsb.ecommercewhitelabel.data.repository.HomeRepositoryImpl
import com.pvsb.ecommercewhitelabel.data.repository.CartRepository
import com.pvsb.ecommercewhitelabel.data.repository.CartRepositoryImpl
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