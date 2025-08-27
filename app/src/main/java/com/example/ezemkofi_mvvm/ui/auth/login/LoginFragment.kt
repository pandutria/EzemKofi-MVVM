package com.example.ezemkofi_mvvm.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ezemkofi_mvvm.R
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.databinding.FragmentLoginBinding
import com.example.ezemkofi_mvvm.utils.Helper
import com.example.ezemkofi_mvvm.utils.State

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(AuthRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }

        binding.etUsername.setText("string")
        binding.etPassword.setText("string")

        handleLogin()


        return  binding.root
    }

    fun handleLogin() {
        binding.apply {
            btnLogin.setOnClickListener {
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()
                viewModel.login(username, password, requireContext())
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    Helper.toast(requireContext(), "Loading...")
                }
                is State.Success -> {
//                    Helper.log("SaveToken", TokenSharedPrefrence(requireContext()).getToken())
                    Helper.toast(requireContext(), "Login Success!")
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is State.Error -> {
                    Helper.toast(requireContext(), state.message)
                }
            }
        }
    }

}