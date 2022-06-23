package com.pvsb.ecommercewhitelabel.data.firestore.di

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
    fun provideHomeDocument(store: FirebaseFirestore): DocumentReference{

        val path = "data/home"
        return store.document(path)
    }
}