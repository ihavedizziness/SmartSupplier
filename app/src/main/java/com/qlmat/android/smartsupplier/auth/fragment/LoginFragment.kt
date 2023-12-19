package com.qlmat.android.smartsupplier.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.qlmat.android.smartsupplier.MainActivity
import com.qlmat.android.smartsupplier.R
import com.qlmat.android.smartsupplier.data.state.LoginState
import com.qlmat.android.smartsupplier.databinding.FragmentLoginBinding
import com.qlmat.android.smartsupplier.network.AuthManager
import com.qlmat.android.smartsupplier.auth.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewBinding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActions()
        initObserver()
        autoLogin()
    }

    private fun initActions() = with(viewBinding) {
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            viewModel.login(email, password)
        }

        layoutSignUp.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.container, RegistrationFragment())
            }
        }
    }

    private fun initObserver() {
        viewModel.loginStateLiveData.observe(viewLifecycleOwner, ::handleLoginState)
    }

    private fun handleLoginState(state: LoginState) {
        when (state) {
            is LoginState.Failed -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is LoginState.Loading -> {}
            is LoginState.Success -> {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun autoLogin() {
        AuthManager.checkAuthState { isLogged ->
            if (isLogged) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                activity?.finish()
            }
        }
    }
}