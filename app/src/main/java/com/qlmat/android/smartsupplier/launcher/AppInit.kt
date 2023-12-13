package com.qlmat.android.smartsupplier.launcher

import android.app.Application
import com.google.firebase.FirebaseApp
import com.qlmat.android.smartsupplier.di.DiContainer

class AppInit: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        DiContainer.startKoinDi(this)
    }
}