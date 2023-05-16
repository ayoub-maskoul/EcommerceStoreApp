package com.example.mystage.dio

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideirebaesAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideirebaesFirestoreDatebaes() = Firebase.firestore
}