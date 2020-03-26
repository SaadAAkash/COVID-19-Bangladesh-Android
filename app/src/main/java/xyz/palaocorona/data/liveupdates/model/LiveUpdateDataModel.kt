package xyz.palaocorona.data.liveupdates.model

import com.google.gson.annotations.SerializedName

data class LiveUpdateDataModel (
    @SerializedName("value") var value: Int = 0,
    @SerializedName("detail") var detail: String = ""
)
