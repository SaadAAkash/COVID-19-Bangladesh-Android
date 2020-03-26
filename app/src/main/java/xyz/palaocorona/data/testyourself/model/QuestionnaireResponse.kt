package xyz.palaocorona.data.testyourself.model

import com.google.gson.annotations.SerializedName
import xyz.palaocorona.data.base.BaseResponse

data class QuestionnaireResponse(
    
    @SerializedName("views")
    var views: MutableList<Question> = mutableListOf()

): BaseResponse()