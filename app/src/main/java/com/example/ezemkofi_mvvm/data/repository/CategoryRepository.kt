package com.example.ezemkofi_mvvm.data.repository

import com.example.ezemkofi_mvvm.data.model.response.CategoryResponse
import com.example.ezemkofi_mvvm.data.network.RetrofitInstance
import retrofit2.Response

class CategoryRepository {
    suspend fun getAllCategories(): Response<List<CategoryResponse>> {
        return RetrofitInstance.api.getAllCategories()
    }
}