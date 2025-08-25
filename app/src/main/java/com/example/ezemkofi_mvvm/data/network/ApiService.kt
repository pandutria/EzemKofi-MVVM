package com.example.ezemkofi_mvvm.data.network

import com.example.ezemkofi_mvvm.data.model.register.RegisterRequest
import com.example.ezemkofi_mvvm.data.model.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<String>
}