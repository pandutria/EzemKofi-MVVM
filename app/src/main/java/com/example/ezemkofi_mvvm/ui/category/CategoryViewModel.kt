package com.example.ezemkofi_mvvm.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ezemkofi_mvvm.data.model.category.CategoryResponse
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.data.repository.CategoryRepository
import com.example.ezemkofi_mvvm.ui.main.MainViewModel
import com.example.ezemkofi_mvvm.utils.State
import kotlinx.coroutines.launch

class CategoryViewModel(private val repo: CategoryRepository): ViewModel() {
    private val _getAllCategoriesResult = MutableLiveData<State<List<CategoryResponse>>>()
    val getAllCategoriesResult: LiveData<State<List<CategoryResponse>>> get() = _getAllCategoriesResult

    fun getAllCategories() {
        viewModelScope.launch {
            try {
                val res = repo.getAllCategories()
                if (res.isSuccessful) _getAllCategoriesResult.postValue(State.Success(res.body()!!))
                else _getAllCategoriesResult.postValue(State.Error("Eror : ${res.body()}"))
            } catch (e:Exception) {
                _getAllCategoriesResult.postValue(State.Error("Eror : ${e.message}"))
            }
        }
    }
}

class CategoryViewModelFactory(private val repo: CategoryRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}