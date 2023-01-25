package com.example.movieapp.data.source.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "upcoming_remote_keys")
    data class UpcomingRemoteKeys(
    @PrimaryKey
    val MovieId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    )
