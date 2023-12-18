package com.qlmat.android.smartsupplier.network.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.network.auth.fragment.LoginFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportFragmentManager.commit {
            replace(R.id.container, LoginFragment())
        }
    }
}