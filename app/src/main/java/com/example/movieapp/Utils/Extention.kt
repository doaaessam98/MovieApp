package com.example.movieapp.Utils

import androidx.compose.ui.text.style.TextAlign.Companion.values
import com.example.movieapp.models.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)



