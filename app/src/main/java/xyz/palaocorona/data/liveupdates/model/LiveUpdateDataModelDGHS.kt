package xyz.palaocorona.data.liveupdates.model

import com.google.gson.annotations.SerializedName

data class LiveUpdateDataModelDGHS (
    @SerializedName("quarantined") var quarantined: LiveUpdateDataModel = LiveUpdateDataModel(),
    @SerializedName("released") var released: LiveUpdateDataModel = LiveUpdateDataModel(),
    @SerializedName("isolated")var isolated: LiveUpdateDataModel = LiveUpdateDataModel(),
    @SerializedName("total_isolation_completed")var total_isolation_completed: LiveUpdateDataModel = LiveUpdateDataModel(),
    @SerializedName("lastUpdated") var lastUpdated: String = ""
)

