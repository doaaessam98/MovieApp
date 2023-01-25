package com.example.movieapp.data.source.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trending_remote_keys")
    data class TrendingRemoteKeys(
    @PrimaryKey
    val MovieId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    )
