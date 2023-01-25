package com.example.movieapp.data.source.local.db.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.source.local.db.entities.PopularRemoteKeys
import com.example.movieapp.data.source.local.db.entities.TrendingRemoteKeys

@Dao
interface PopularRemoteKeysDao {
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertAll(remoteKey: List<PopularRemoteKeys>)

 @Query("SELECT * FROM poplar_remote_keys WHERE MovieId=:id ")
 suspend fun remoteKeysMovieId(id:Int): PopularRemoteKeys?

 @Query("DELETE FROM poplar_remote_keys ")
 suspend fun clearRemoteKeys()








}