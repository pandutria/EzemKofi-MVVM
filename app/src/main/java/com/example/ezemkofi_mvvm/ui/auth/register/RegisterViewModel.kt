package com.example.ezemkofi_mvvm.ui.auth.register

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.utils.State
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<State>()
    val registerResult: LiveData<State> get() = _registerResult

    fun register(
        username: String,
        fullname: String,
        email: String,
        password: String,
        context: Context
    ) {
        if (username == "" || fullname == "" || email == "" || password == "") {
            _registerResult.value = State.Error("All field must be filled")
            return
        }

        viewModelScope.launch {
            _registerResult.postValue(State.Loading)
            try {
                val res = repo.register(username, fullname, email, password, context)

                if (res.isSuccessful) _registerResult.postValue(State.Success(res.body()!!))
                else _registerResult.postValue(State.Error("Eror : ${res.body()}"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

class RegisterViewModelFactory(private val repo: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}