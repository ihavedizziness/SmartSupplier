package com.qlmat.android.smartsupplier.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.auth.fragment.LoginFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutInflater.inflate(R.layout.activity_auth, null, false))
        supportFragmentManager.commit {
            replace(R.id.container, LoginFragment())
        }
    }
}