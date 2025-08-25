package com.example.ezemkofi_mvvm.data.model.register

data class RegisterRequest(
    val username: String? = null,
    val fullname: String? = null,
    val email: String? = null,
    val password: String? = null,
)
