package com.example.movieapp.data.source.local.db.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.source.local.db.entities.TrendingRemoteKeys

@Dao
interface TrendingRemoteKeysDao {
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertAll(remoteKey: List<TrendingRemoteKeys>)

 @Query("SELECT * FROM trending_remote_keys WHERE MovieId=:id ")
 suspend fun remoteKeysMovieId(id:Int): TrendingRemoteKeys?

 @Query("DELETE FROM trending_remote_keys")
 suspend fun clearRemoteKeys()








}