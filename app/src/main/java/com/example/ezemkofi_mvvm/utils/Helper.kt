package com.example.ezemkofi_mvvm.utils

import android.content.Context
import android.widget.Toast

object Helper {
    fun toast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}