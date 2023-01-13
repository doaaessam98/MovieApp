package com.example.movieapp.models


import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "genre")

data class Genre(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)