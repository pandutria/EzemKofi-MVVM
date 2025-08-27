package com.example.ezemkofi_mvvm.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ezemkofi_mvvm.data.local.TokenSharedPrefrence
import com.example.ezemkofi_mvvm.data.model.auth.AuthResponse
import com.example.ezemkofi_mvvm.data.repository.AuthRepository
import com.example.ezemkofi_mvvm.utils.State
import kotlinx.coroutines.launch

class MainViewModel(private val repo: AuthRepository): ViewModel() {
    private val _meResult = MutableLiveData<State<AuthResponse>>()
    val meResult: LiveData<State<AuthResponse>> get() = _meResult

    fun me(context: Context) {
        if (TokenSharedPrefrence(context).getToken() == null) {
            _meResult.value = State.Error("Token is null")
            return
        }

        viewModelScope.launch {
            _meResult.postValue(State.Loading)
            try {
                val res = repo.me(context)

                if (res.isSuccessful) _meResult.postValue(State.Success(res.body()!!))
                else _meResult.postValue(State.Error("Eror : ${res.body()}"))
            } catch (e: Exception) {
                _meResult.postValue(State.Error("Eror : ${e.message}"))
            }
        }
    }
}

class MainViewModelFactory(private val repo: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}