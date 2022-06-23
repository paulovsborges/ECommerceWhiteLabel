package com.pvsb.ecommercewhitelabel.domain.di

import com.pvsb.ecommercewhitelabel.domain.HomeRepository
import com.pvsb.ecommercewhitelabel.domain.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideHomeImpl(impl: HomeRepositoryImpl): HomeRepository
}