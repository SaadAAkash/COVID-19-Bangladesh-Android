package ninja.saad.palaocorona.data.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponse : Serializable {

    @SerializedName("data")
    val data: String = ""
    @SerializedName("status")
    val status: Int = 0
    @SerializedName("message")
    val message: String = ""

}