package vn.hungnx.themovies.data.source.remote.response

import com.google.gson.annotations.SerializedName
import vn.hungnx.themovies.data.Movie

abstract class BaseListResponse<Item>(
    @SerializedName("page") val page:Int?=null,
    @SerializedName("total_results") val totalResults:Int?=null,
    @SerializedName("total_pages") val totalPages:Int?=null,
    @SerializedName("results") val results:List<Item>?=null
)