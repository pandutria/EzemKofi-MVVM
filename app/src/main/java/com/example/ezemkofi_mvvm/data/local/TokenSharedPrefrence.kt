package com.example.ezemkofi_mvvm.data.local

import android.content.Context

class TokenSharedPrefrence(context: Context)  {
    val tokenPref = "token_pref"
    val tokenKey = "tokenKey"

    private val shared = context.getSharedPreferences(tokenPref,Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        shared.edit().putString(tokenKey, token).apply()
    }

    fun getToken(): String {
        return shared.getString(tokenKey, null).toString()
    }

    fun removeToken() {
        shared.edit().remove(tokenKey).apply()
    }
}