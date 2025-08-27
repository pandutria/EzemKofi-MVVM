package com.example.ezemkofi_mvvm.ui.auth.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.ui.auth.register.RegisterViewModel
import com.example.ezemkofi_mvvm.utils.Helper
import com.example.ezemkofi_mvvm.utils.State
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repo: AuthRepository): ViewModel() {
    private val _loginResult = MutableLiveData<State<String>>()
    val loginResult: LiveData<State<String>> get() = _loginResult

    fun login(
        username: String,
        password: String,
        context: Context
    ) {
        if (username == "" || password == "") {
            _loginResult.value = State.Error("All field must be filled")
            return
        }

        viewModelScope.launch {
            _loginResult.postValue(State.Loading)
            try {
                val res = repo.login(username, password, context)

                if (res.isSuccessful) _loginResult.postValue(State.Success(res.body()!!))
                else _loginResult.postValue(State.Error("Eror : ${res.body()}"))
            } catch (e: Exception) {
                e.printStackTrace()
                _loginResult.postValue(State.Error("Eror : ${e.message}"))
                Helper.log("Eror", e.message!!)
            }
        }
    }
}

class LoginViewModelFactory(private val repo: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}