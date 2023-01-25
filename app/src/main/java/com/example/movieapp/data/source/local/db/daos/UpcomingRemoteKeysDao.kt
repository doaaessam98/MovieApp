package com.example.movieapp.data.source.local.db.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.source.local.db.entities.TrendingRemoteKeys
import com.example.movieapp.data.source.local.db.entities.UpcomingRemoteKeys

@Dao
interface UpcomingRemoteKeysDao {
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertAll(remoteKey: List<UpcomingRemoteKeys>)

 @Query("SELECT * FROM upcoming_remote_keys WHERE MovieId=:id ")
 suspend fun remoteKeysMovieId(id:Int): UpcomingRemoteKeys?

 @Query("DELETE FROM upcoming_remote_keys ")
 suspend fun clearRemoteKeys()








}