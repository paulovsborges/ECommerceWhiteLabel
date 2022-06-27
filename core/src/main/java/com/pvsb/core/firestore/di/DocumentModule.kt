package com.pvsb.core.firestore.di

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DocumentModule {

    @Provides
    @HomeDocumentReference
    fun provideHomeDocument(store: FirebaseFirestore): DocumentReference {

        val path = "data/home"
        return store.document(path)
    }

    @Provides
    @CartDocumentReference
    fun provideCartDocument(store: FirebaseFirestore): DocumentReference {

        val path = "data/cart"
        return store.document(path)
    }
}