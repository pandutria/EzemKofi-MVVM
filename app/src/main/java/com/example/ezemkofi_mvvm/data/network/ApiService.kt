package com.example.ezemkofi_mvvm.data.network

import com.example.ezemkofi_mvvm.data.model.auth.AuthResponse
import com.example.ezemkofi_mvvm.data.model.auth.LoginRequest
import com.example.ezemkofi_mvvm.data.model.auth.RegisterRequest
import com.example.ezemkofi_mvvm.data.model.category.CategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<String>

    @POST("api/auth")
    suspend fun login(@Body request: LoginRequest): Response<String>

    @GET("api/me")
    suspend fun me(@Header("Authorization") token: String): Response<AuthResponse>

    @GET("api/coffee-category")
    suspend fun getAllCategories(): Response<List<CategoryResponse>>
}