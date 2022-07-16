package com.pvsb.ecommercewhitelabel.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KtorModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {

        val client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }

            install(JsonFeature) {

                val json = kotlinx.serialization.json.Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }

                serializer = KotlinxSerializer(json)
            }
        }

        return client
    }
}