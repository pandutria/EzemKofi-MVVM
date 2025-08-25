package com.example.ezemkofi_mvvm.data.repository

import com.example.ezemkofi_mvvm.data.model.register.RegisterRequest
import com.example.ezemkofi_mvvm.data.model.register.RegisterResponse
import com.example.ezemkofi_mvvm.data.network.RetrofitInstance
import retrofit2.Response

class AuthRepository {
    suspend fun register(
        username: String,
        fullname: String,
        email: String,
        password: String
    ): Response<String> {
        return RetrofitInstance.api.register(RegisterRequest(username, fullname, email, password))
    }
}