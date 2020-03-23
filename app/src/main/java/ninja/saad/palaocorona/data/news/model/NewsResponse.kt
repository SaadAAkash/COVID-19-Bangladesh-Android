package ninja.saad.palaocorona.data.news.model

import com.google.gson.annotations.SerializedName
import ninja.saad.palaocorona.data.base.BaseResponse

data class NewsResponse(
    
    @SerializedName("data")
    var data: MutableList<News> = mutableListOf()

): BaseResponse()