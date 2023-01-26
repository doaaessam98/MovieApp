package com.example.movieapp.data.source.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movie WHERE isPopular = 1 ORDER BY popularity DESC")
    fun getPopularityMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie WHERE isTrending = 1 ORDER BY voteAverage DESC")
    fun getTrendingMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie WHERE isUpcoming=1  ORDER BY releaseDate DESC")
    fun getUpcomingMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getSearchMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie WHERE genreIds LIKE :genre  ORDER BY popularity DESC ")
    fun getCategoryMovies(genre: String): PagingSource<Int, Movie>

    @Query("UPDATE movie set isTrending = 0 WHERE  isTrending = 1")
    fun clearTrendingMovies()

    @Query("UPDATE movie set isPopular = 0 WHERE  isPopular = 1")
    fun clearPopularMovies()

    @Query("UPDATE movie set isUpcoming = 0 WHERE  isUpcoming = 1")
    fun clearUpcomingMovies()

    @Query("DELETE FROM MOVIE WHERE  isUpcoming = 0 AND isPopular=0 AND isTrending=0")
    fun clearMovies()

     @Query("UPDATE  movie set isFav = 1 WHERE id =:id")
      suspend fun addFavourite(id:Int):Int

    @Query("UPDATE  movie set isFav = 0 WHERE id =:id")
    suspend fun removeFromFavourite(id:Int):Int

    @Query("SELECT * FROM movie WHERE isFav = 1 ")
    fun getFavMovie(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE  isFav = 1 AND ( overview LIKE :query  OR title LIKE :query) ")
    fun getSearchFavMovie(query: String): Flow<List<Movie>>



//    @Query("SELECT COUNT (id)  FROM  Favourite WHERE id =:id")
//    suspend fun isFavourite(id:Int):Int


    @Query("SELECT EXISTS(SELECT * FROM movie WHERE id =:id And isFav = 1)")
    suspend fun isFavourite(id:Int):Boolean

    @Query("SELECT * FROM movie WHERE id =:id ")
    suspend fun isFavourite1(id:Int):Movie

    @Query("SELECT * FROM movie WHERE  id =:id ")
    suspend fun isPopular(id:Int):Movie

    @Query("SELECT * FROM movie WHERE genreIds LIKE :genreId AND (overview LIKE :query OR title LIKE :query)")
    fun getSearchCategoryMovies(query: String, genreId: String): PagingSource<Int, Movie>

 //    @Query("SELECT * FROM movie WHERE  id =:id ANd isTrending = 1")
//    suspend fun isTrending(id:Int)

 //    @Query("SELECT * FROM movie WHERE  id =:id  AND isUpcoming = 1 ")
//    suspend fun isUpcoming(id:Int)

}


