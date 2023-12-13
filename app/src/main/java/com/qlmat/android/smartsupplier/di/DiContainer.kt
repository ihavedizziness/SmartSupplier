package com.qlmat.android.smartsupplier.di

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

object DiContainer {

    val firestoreModule = module {
        single { provideFirestore() }
    }

    private fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    val viewModelModule = module {

    }

    fun startKoinDi(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                firestoreModule,

            )
        }
    }
}