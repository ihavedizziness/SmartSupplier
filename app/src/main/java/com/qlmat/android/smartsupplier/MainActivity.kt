package com.qlmat.android.smartsupplier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.databinding.ActivityMainBinding
import com.qlmat.android.smartsupplier.network.auth.AuthActivity
import com.qlmat.android.smartsupplier.network.auth.AuthManager

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with (viewBinding) {
            btnLogout.setOnClickListener {
                AuthManager.logOut()
                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
            }
        }
    }
}