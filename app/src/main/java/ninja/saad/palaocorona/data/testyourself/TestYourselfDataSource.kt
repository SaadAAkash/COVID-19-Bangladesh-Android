package ninja.saad.palaocorona.data.testyourself

import io.reactivex.Single
import ninja.saad.palaocorona.base.data.network.onResponse
import ninja.saad.palaocorona.data.testyourself.model.Question
import javax.inject.Inject

class TestYourselfDataSource @Inject constructor(private val apiService: TestYourselfRestService) {
    
    fun getQuestionnaire(): Single<MutableList<Question>> {
        return apiService.getquestionnaire()
            .onResponse()
            .map {
                it.views
            }
    }
}