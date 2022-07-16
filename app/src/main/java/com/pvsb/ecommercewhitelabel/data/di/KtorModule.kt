package com.pvsb.ecommercewhitelabel.data.di

import android.util.Log
import com.pvsb.ecommercewhitelabel.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KtorModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        val client = HttpClient(Android) {
            defaultRequest {
                host = BuildConfig.BASE_URL_POSTAL_CODE
                url {
                    protocol = URLProtocol.HTTPS
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KTOR_INFO", message)
                    }
                }
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