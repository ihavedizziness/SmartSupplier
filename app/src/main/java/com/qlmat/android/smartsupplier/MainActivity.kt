package com.qlmat.android.smartsupplier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.databinding.ActivityMainBinding
import com.qlmat.android.smartsupplier.ui.history.OrderHistoryFragment
import com.qlmat.android.smartsupplier.ui.home.HomeFragment
import com.qlmat.android.smartsupplier.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutInflater.inflate(R.layout.activity_main, null, false))

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
                    supportFragmentManager.commit {
                        replace(R.id.containerView, OrderHistoryFragment())
                    }
                    true
                }
                R.id.bottom_nav_profile -> {
                    supportFragmentManager.commit {
                        replace(R.id.containerView, ProfileFragment())
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