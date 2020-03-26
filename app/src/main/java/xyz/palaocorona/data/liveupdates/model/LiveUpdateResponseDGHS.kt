package xyz.palaocorona.data.liveupdates.model

import com.google.gson.annotations.SerializedName

data class LiveUpdateResponseDGHS(
    
    @SerializedName("data")
    var data: LiveUpdateDataModelDGHS = LiveUpdateDataModelDGHS()

)