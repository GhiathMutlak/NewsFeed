package com.giyas.newsfeed.ui.API

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsData {

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("totalResults")
    @Expose
    var totalResults  = 0

    @SerializedName("articles")
    @Expose
    var articles: List<Articles>? = null


}