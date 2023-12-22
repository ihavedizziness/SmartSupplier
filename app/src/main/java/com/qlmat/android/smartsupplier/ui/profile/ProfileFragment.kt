package com.qlmat.android.smartsupplier.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.arch.SharedPreferencesRepo
import com.qlmat.android.smartsupplier.databinding.FragmentProfileBinding
import com.qlmat.android.smartsupplier.network.AuthManager
import com.qlmat.android.smartsupplier.ui.home.HomeFragment
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewBinding: FragmentProfileBinding by viewBinding()
    private val sharedPreferencesRepository: SharedPreferencesRepo by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProfile()
        initActions()
    }

    private fun initProfile() = with(viewBinding) {
        textViewProfileUserId.text = sharedPreferencesRepository.getUserId()
        textViewProfileUserEmail.text = sharedPreferencesRepository.getUserEmail()
        textViewProfileUserPhoneNumber.text = sharedPreferencesRepository.getUserPhoneNumber()
    }

    private fun initActions() = with(viewBinding) {
        buttonLogout.setOnClickListener {
            AuthManager.logOut()
            sharedPreferencesRepository.clearUser()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.containerView, HomeFragment())
                .commit()
        }
    }

}