package com.pvsb.ecommercewhitelabel.data.di

import android.util.Log
import com.pvsb.ecommercewhitelabel.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.host
import io.ktor.http.URLProtocol
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KtorModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        val client = HttpClient(Android) {
            defaultRequest {
                host = BuildConfig.BASE_URL_ZIP_CODE
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
