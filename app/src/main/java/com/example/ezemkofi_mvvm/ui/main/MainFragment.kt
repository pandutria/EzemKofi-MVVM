package com.example.ezemkofi_mvvm.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.ezemkofi_mvvm.R
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.data.repository.CategoryRepository
import com.example.ezemkofi_mvvm.databinding.FragmentMainBinding
import com.example.ezemkofi_mvvm.ui.auth.login.LoginViewModel
import com.example.ezemkofi_mvvm.ui.category.CategoryAdapter
import com.example.ezemkofi_mvvm.ui.category.CategoryViewModel
import com.example.ezemkofi_mvvm.ui.category.CategoryViewModelFactory
import com.example.ezemkofi_mvvm.utils.Helper
import com.example.ezemkofi_mvvm.utils.State

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(AuthRepository())
    }
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(CategoryRepository())
    }

    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater)

        categoryAdapter = CategoryAdapter { category ->

        }

        handleGreatingText()
        handleGetAllCategories()

        return binding.root
    }

    fun handleGreatingText() {
        mainViewModel.me(requireContext())

        mainViewModel.meResult.observe(viewLifecycleOwner) {state ->
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

    fun handleGetAllCategories() {
        categoryViewModel.getAllCategories()

        categoryViewModel.getAllCategoriesResult.observe(viewLifecycleOwner) {state ->
            when (state) {
                is State.Loading -> {}
                is State.Success -> {
                    categoryAdapter.setData(state.data)
                    binding.rvCategory.adapter = categoryAdapter
                }
                is State.Error -> {
                    Helper.toast(requireContext(), state.message)
                }
            }
        }
    }
}