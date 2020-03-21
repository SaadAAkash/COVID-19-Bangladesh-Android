package ninja.saad.palaocorona.data.testyourself

import io.reactivex.Single
import ninja.saad.palaocorona.base.data.network.onResponse
import ninja.saad.palaocorona.data.testyourself.model.Questionnaire
import javax.inject.Inject

class TestYourselfDataSource @Inject constructor(private val apiService: TestYourselfRestService) {
    
    fun getQuestionnaire(): Single<MutableList<Questionnaire>> {
        return apiService.getquestionnaire()
            .onResponse()
            .map {
                it.views
            }
    }
}