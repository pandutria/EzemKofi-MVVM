package com.example.ezemkofi_mvvm.data.repository

import android.content.Context
import com.example.ezemkofi_mvvm.data.local.TokenSharedPrefrence
import com.example.ezemkofi_mvvm.data.model.response.AuthResponse
import com.example.ezemkofi_mvvm.data.model.request.LoginRequest
import com.example.ezemkofi_mvvm.data.model.request.RegisterRequest
import com.example.ezemkofi_mvvm.data.network.RetrofitInstance
import retrofit2.Response

class AuthRepository {
    suspend fun register(
        username: String,
        fullname: String,
        email: String,
        password: String,
        context: Context
    ): Response<String> {
        val res = RetrofitInstance.api.register(RegisterRequest(username, fullname, email, password))
        TokenSharedPrefrence(context).saveToken(res.body()!!)
        return res
    }

    suspend fun login(
        username: String,
        password: String,
        context: Context
    ) : Response<String> {
        val res = RetrofitInstance.api.login(LoginRequest(username, password))
        TokenSharedPrefrence(context).saveToken(res.body()!!)
        return res
    }

    suspend fun me(context: Context)
    : Response<AuthResponse> {
        val token = TokenSharedPrefrence(context).getToken()
        val res = RetrofitInstance.api.me("Bearer $token")
        return res
    }
}