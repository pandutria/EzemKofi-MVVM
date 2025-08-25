package com.example.ezemkofi_mvvm.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ezemkofi_mvvm.data.model.register.RegisterResponse
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val repo: AuthRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<Response<String>>()
    val registerResult: LiveData<Response<String>> get() = _registerResult

    fun register(
        username: String,
        fullname: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                val res = repo.register(username, fullname, email, password)
                _registerResult.postValue(res)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

class RegisterViewModelFactory(private val repository: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}