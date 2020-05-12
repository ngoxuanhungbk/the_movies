package vn.hungnx.themovies.data

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import vn.hungnx.themovies.data.source.local.MovieConverter

@Entity(tableName = "movies")
@Parcelize
data class Movie @JvmOverloads constructor(
    @SerializedName("popularity")
    val popularity:Double,
    @SerializedName("vote_count")
    val voteCount:Int,
    @SerializedName("video")
    val video:Boolean,
    @SerializedName("poster_path")
    val posterPath:String,
    @PrimaryKey
    @SerializedName("id")
    val id:Int,
    @SerializedName("adult")
    val adult:Boolean,
    @SerializedName("backdrop_path")
    val backdropPath:String,
    @SerializedName("original_language")
    val originalLanguage:String,
    @SerializedName("original_title")
    val originalTitle:String,
    @SerializedName("genre_ids")
    val genreIds:List<Int>,
    @SerializedName("title")
    val title:String,
    @SerializedName("vote_average")
    val voteAverage:Double,
    @SerializedName("overview")
    val overview:String,
    @SerializedName("release_date")
    val releaseDate:String
):Parcelable{
    @Ignore
    var isFavorite:Boolean = false
    fun getFullPosterPath() = if (posterPath.isNullOrBlank()) null else "https://image.tmdb.org/t/p/w200$posterPath"
}