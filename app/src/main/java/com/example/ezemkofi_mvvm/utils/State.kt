package com.example.ezemkofi_mvvm.utils

sealed class State {
    data class Success(val message: String) : State()
    data class Error(val error: String) : State()
    object Loading : State()
}