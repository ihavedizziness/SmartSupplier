package com.qlmat.android.smartsupplier.di

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo
import com.qlmat.android.smartsupplier.data.repository.UserRepo
import com.qlmat.android.smartsupplier.network.AuthManager
import com.qlmat.android.smartsupplier.auth.viewmodel.LoginViewModel
import com.qlmat.android.smartsupplier.auth.viewmodel.RegisterViewModel
import com.qlmat.android.smartsupplier.data.repository.OrderRepo
import com.qlmat.android.smartsupplier.data.repository.ProductRepo
import com.qlmat.android.smartsupplier.data.repository.WarehouseRepo
import com.qlmat.android.smartsupplier.ui.home.HomeViewModel
import com.qlmat.android.smartsupplier.ui.order.OrderViewModel
import com.qlmat.android.smartsupplier.ui.product.ProductDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object DiContainer {

    private val databaseModule = module {
        single { FirebaseFirestore.getInstance() }
        single { UserRepo(get()) }
        single { ProductRepo() }
        single { OrderRepo() }
        single { WarehouseRepo() }
    }

    private val networkModule = module {
        single { AuthManager }
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
        viewModel {
            ProductDetailViewModel(
                productRepo = get()
            )
        }
        viewModel{
            OrderViewModel(
                orderRepo = get(),
                warehouseRepo = get()
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