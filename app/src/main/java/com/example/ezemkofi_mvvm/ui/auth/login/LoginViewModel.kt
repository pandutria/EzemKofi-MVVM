package com.example.ezemkofi_mvvm.ui.auth.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.ui.auth.register.RegisterViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repo: AuthRepository): ViewModel() {
    private val _loginResult = MutableLiveData<Response<String>>()
    val loginResult: LiveData<Response<String>> get() = _loginResult

    fun login(
        username: String,
        password: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val res = repo.login(username, password, context)
                _loginResult.postValue(res)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

class LoginViewModelFactory(private val repo: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}