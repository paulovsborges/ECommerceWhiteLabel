package com.pvsb.ecommercewhitelabel.data.di

import com.pvsb.ecommercewhitelabel.data.repository.AuthRepository
import com.pvsb.ecommercewhitelabel.data.repository.AuthRepositoryImpl
import com.pvsb.ecommercewhitelabel.data.repository.CartRepository
import com.pvsb.ecommercewhitelabel.data.repository.CartRepositoryImpl
import com.pvsb.ecommercewhitelabel.data.repository.HomeRepository
import com.pvsb.ecommercewhitelabel.data.repository.HomeRepositoryImpl
import com.pvsb.ecommercewhitelabel.data.repository.NetworkRepository
import com.pvsb.ecommercewhitelabel.data.repository.NetworkRepositoryImpl
import com.pvsb.ecommercewhitelabel.data.repository.ProfileRepository
import com.pvsb.ecommercewhitelabel.data.repository.ProfileRepositoryImpl
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
