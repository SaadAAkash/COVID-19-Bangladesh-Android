package ninja.saad.palaocorona.data.news.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("title") var title: String = "",
    @SerializedName("subtitle") var subtitle: String = "",
    @SerializedName("time")var time: String = "",
    @SerializedName("source") var source: String = "",
    @SerializedName("imagesrc") var imageSrc: String = ""
)