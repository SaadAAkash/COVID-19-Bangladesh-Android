package xyz.palaocorona.data.main.model

import com.google.gson.annotations.SerializedName

data class AppUpdateResponse(
    @SerializedName("data") var data: Boolean = false
)