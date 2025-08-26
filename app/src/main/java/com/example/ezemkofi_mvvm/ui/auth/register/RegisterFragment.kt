package com.example.ezemkofi_mvvm.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ezemkofi_mvvm.R
import com.example.ezemkofi_mvvm.data.local.TokenSharedPrefrence
import com.example.ezemkofi_mvvm.data.network.ApiService
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.databinding.FragmentRegisterBinding
import com.example.ezemkofi_mvvm.utils.Helper
import com.example.ezemkofi_mvvm.utils.State

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(AuthRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
        }

        handleRegister()

        return binding.root
    }

    fun handleRegister() {
        binding.apply {
            btnRegister.setOnClickListener {
                val username = binding.etUsername.text.toString()
                val fullname = binding.etFullname.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()

                viewModel.register(username, fullname, email, password, requireContext())
            }

            viewModel.registerResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is State.Loading -> {
                        Helper.toast(requireContext(), "Loading...")
                    }
                    is State.Success -> {
                        Helper.log("SaveToken", TokenSharedPrefrence(requireContext()).getToken())
                        Helper.toast(requireContext(), "Register Success! ${state.message}")
                        findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                    }
                    is State.Error -> {
                        Helper.toast(requireContext(), state.error)
                    }
                }
            }
        }
    }

}