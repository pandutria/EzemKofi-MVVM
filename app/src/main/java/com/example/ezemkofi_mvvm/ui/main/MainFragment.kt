package com.example.ezemkofi_mvvm.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.ezemkofi_mvvm.R
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.databinding.FragmentMainBinding
import com.example.ezemkofi_mvvm.ui.auth.login.LoginViewModel
import com.example.ezemkofi_mvvm.utils.Helper
import com.example.ezemkofi_mvvm.utils.State

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(AuthRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater)

        handleGreatingText()

        return binding.root
    }

    fun handleGreatingText() {
        viewModel.me(requireContext())

        viewModel.meResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {
                    binding.tvName.text = "XXXXXXXX"
                }
                is State.Success -> {
                    binding.tvName.text = state.data.fullName
                    Helper.toast(requireContext(), "Sucess to get name")
                }
                is State.Error -> {
                    Helper.toast(requireContext(), state.message)
                }
            }
        }
    }
}