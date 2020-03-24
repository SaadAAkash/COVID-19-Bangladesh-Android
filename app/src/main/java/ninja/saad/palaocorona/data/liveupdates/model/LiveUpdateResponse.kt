package ninja.saad.palaocorona.data.liveupdates.model

import com.google.gson.annotations.SerializedName

data class LiveUpdateResponse(
    @SerializedName("confirmed") var confirmed: LiveUpdateDataModel = LiveUpdateDataModel(),
    @SerializedName("recovered") var recovered: LiveUpdateDataModel = LiveUpdateDataModel(),
    @SerializedName("deaths")var deaths: LiveUpdateDataModel = LiveUpdateDataModel(),
    @SerializedName("lastUpdate") var lastUpdate: String = ""
)