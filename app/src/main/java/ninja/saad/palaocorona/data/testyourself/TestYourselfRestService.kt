package ninja.saad.palaocorona.data.testyourself

import io.reactivex.Single
import ninja.saad.palaocorona.data.testyourself.model.QuestionnaireResponse
import retrofit2.Response
import retrofit2.http.GET

interface TestYourselfRestService {
    
    @GET("self/test/get/questions")
    fun getquestionnaire(): Single<Response<QuestionnaireResponse>>
}