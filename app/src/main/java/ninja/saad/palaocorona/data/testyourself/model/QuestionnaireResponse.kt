package ninja.saad.palaocorona.data.testyourself.model

import com.google.gson.annotations.SerializedName
import ninja.saad.palaocorona.data.base.BaseResponse

data class QuestionnaireResponse(
    
    @SerializedName("views")
    var views: MutableList<Question> = mutableListOf()

): BaseResponse()