package com.example.movieapp.models


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

import kotlinx.serialization.Serializable



@Entity(tableName ="movie")
@Parcelize
data class Movie(
    @SerializedName("adult")
    val adult: Boolean =false,
//    @SerializedName("backdrop_path")
//    val backdropPath: String="",
    @SerializedName("genre_ids")
       val genreIds: List<Int> = emptyList(),
    @PrimaryKey
    @SerializedName("id")
    val id: Int=0,
    @SerializedName("original_language")
    val originalLanguage: String="",
    @SerializedName("original_title")
    val originalTitle: String="",
    @SerializedName("overview")
    val overview: String="",
    @SerializedName("popularity")
    val popularity: Double=0.0,
    @SerializedName("poster_path")
    val posterPath: String="",
    @SerializedName("release_date")
    val releaseDate: String="",
    @SerializedName("title")
    val title: String="",
    @SerializedName("video")
    val video: Boolean=false,
    @SerializedName("vote_average")
    val voteAverage: Double=0.0,
    @SerializedName("vote_count")
    val voteCount: Int=0,
    var isFav: Boolean=false,
    var page:Int = 0,
    var isTrending:Boolean =false,
    var isPopular:Boolean =false,
    var isUpcoming:Boolean = false

):Parcelable {

}

fun List<Movie>.toDatabaseEntity(type:ApiQuery){
    this.forEach{
           when(type){
               is ApiQuery.Trending->{
                   it.isTrending = true
               }
               is ApiQuery.Popular->{
                   it.isPopular=true
               }
               is ApiQuery.Upcoming->{
                   it.isUpcoming = true
               }
           }

      }
 }

