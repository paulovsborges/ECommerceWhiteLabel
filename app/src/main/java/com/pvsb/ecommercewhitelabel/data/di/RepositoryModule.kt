package com.pvsb.ecommercewhitelabel.data.di

import com.pvsb.ecommercewhitelabel.data.repository.*
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

    @Binds
    fun provideAuthImpl(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideProfileImpl(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun provideNetworkImpl(impl: NetworkRepositoryImpl): NetworkRepository
}