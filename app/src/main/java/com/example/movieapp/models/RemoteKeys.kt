package com.example.movieapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_keys")
    data class RemoteKeys(
    @PrimaryKey
        val MovieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
    )
