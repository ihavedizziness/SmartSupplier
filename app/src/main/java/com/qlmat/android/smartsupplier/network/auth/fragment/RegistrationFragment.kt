package com.qlmat.android.smartsupplier.network.auth.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.data.state.RegistrationState
import com.qlmat.android.smartsupplier.databinding.FragmentRegistrationBinding
import com.qlmat.android.smartsupplier.network.auth.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewBinding: FragmentRegistrationBinding by viewBinding()
    private val viewModel: RegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initActions()
    }

    private fun initActions() = with(viewBinding) {
        buttonRegistration.setOnClickListener {
            val email = editTextEmail.text.toString()
            val phoneNumber = editTextPhoneNumber.text.toString()
            val password = editTextPassword.text.toString()

            viewModel.register(email, phoneNumber, password)
        }

        layoutSignUp.setOnClickListener {
            fragmentManager?.commit {
                replace(R.id.container, LoginFragment())
            }
        }
    }

    private fun initObserver() {
        viewModel.registrationStateLiveData.observe(viewLifecycleOwner, ::handleRegistrationState)
    }

    private fun handleRegistrationState(state: RegistrationState) {
        when (state) {
            is RegistrationState.Failed -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is RegistrationState.Loading -> {}
            is RegistrationState.Success -> {
                val user = state.value
                fragmentManager?.commit {
                    replace(R.id.container, LoginFragment())
                }
            }
        }
    }
}