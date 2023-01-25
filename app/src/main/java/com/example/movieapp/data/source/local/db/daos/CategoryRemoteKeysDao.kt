package com.example.movieapp.data.source.local.db.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.source.local.db.entities.CategoryRemoteKeys
import com.example.movieapp.data.source.local.db.entities.PopularRemoteKeys
import com.example.movieapp.data.source.local.db.entities.TrendingRemoteKeys

@Dao
interface CategoryRemoteKeysDao {
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertAll(remoteKey: List<CategoryRemoteKeys>)

 @Query("SELECT * FROM category_remote_keys WHERE MovieId=:id ")
 suspend fun remoteKeysMovieId(id:Int):CategoryRemoteKeys?

 @Query("DELETE FROM category_remote_keys ")
 suspend fun clearRemoteKeys()








}