package com.qlmat.android.smartsupplier.di

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo
import com.qlmat.android.smartsupplier.data.repository.UserRepo
import com.qlmat.android.smartsupplier.network.AuthManager
import com.qlmat.android.smartsupplier.auth.viewmodel.LoginViewModel
import com.qlmat.android.smartsupplier.auth.viewmodel.RegisterViewModel
import com.qlmat.android.smartsupplier.data.repository.ProductRepo
import com.qlmat.android.smartsupplier.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object DiContainer {

    private val databaseModule = module {
        single { provideFirestore() }
        factory { UserRepo(get()) }
        factory { ProductRepo(get()) }
    }

    private val networkModule = module {
        single { AuthManager }
    }

    private fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    private val viewModelModule = module {
        viewModel {
            RegisterViewModel()
        }
        viewModel {
            LoginViewModel()
        }
        viewModel {
            HomeViewModel(
                productRepo = get()
            )
        }
    }

    private val mainModule = module {
        factory { SharedPreferencesRepo(androidContext()) }
    }

    fun startKoinDi(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                databaseModule,
                networkModule,
                viewModelModule,
                mainModule
            )
        }
    }
}