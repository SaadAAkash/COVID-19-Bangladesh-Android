package xyz.palaocorona.data.testyourself

import io.reactivex.Single
import xyz.palaocorona.data.testyourself.model.QuestionnaireResponse
import retrofit2.Response
import retrofit2.http.GET

interface TestYourselfRestService {
    
    @GET("self/test/get/questions")
    fun getquestionnaire(): Single<Response<QuestionnaireResponse>>
}