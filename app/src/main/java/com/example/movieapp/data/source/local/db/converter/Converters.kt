package com.example.movieapp.data.source.local.db.converter

import androidx.room.TypeConverter
import com.example.movieapp.Utils.fromJson
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromListIntToString(value: List<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListIntFromString(value: String): List<Int> {
        return try {
            Gson().fromJson<List<Int>>(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}