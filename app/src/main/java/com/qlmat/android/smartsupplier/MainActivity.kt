package com.qlmat.android.smartsupplier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo.Companion.NO_VALUE
import com.qlmat.android.smartsupplier.auth.AuthActivity
import com.qlmat.android.smartsupplier.auth.fragment.LoginFragment
import com.qlmat.android.smartsupplier.databinding.ActivityMainBinding
import com.qlmat.android.smartsupplier.ui.history.OrderHistoryFragment
import com.qlmat.android.smartsupplier.ui.home.HomeFragment
import com.qlmat.android.smartsupplier.ui.profile.ProfileFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding()
    private val sharedPreferencesRepository: SharedPreferencesRepo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutInflater.inflate(R.layout.activity_main, null, false))

        if (sharedPreferencesRepository.getUserId() == NO_VALUE) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
        initNavigation()
    }

    private fun initNavigation() = with(viewBinding) {
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_home -> {
                    supportFragmentManager.commit {
                        replace(R.id.containerView, HomeFragment())
                    }
                    true
                }
                R.id.bottom_nav_order_history -> {
                    if (sharedPreferencesRepository.getUserId() == NO_VALUE) {
                        val intent = Intent(this@MainActivity, AuthActivity::class.java)
                        startActivity(intent)
                    } else {
                        supportFragmentManager.commit {
                            replace(R.id.containerView, OrderHistoryFragment())
                        }
                    }
                    true
                }
                R.id.bottom_nav_profile -> {
                    if (sharedPreferencesRepository.getUserId() == NO_VALUE) {
                        val intent = Intent(this@MainActivity, AuthActivity::class.java)
                        startActivity(intent)
                    } else {
                        supportFragmentManager.commit {
                            replace(R.id.containerView, ProfileFragment())
                        }
                    }
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
}