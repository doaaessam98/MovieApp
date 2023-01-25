package com.example.movieapp.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)