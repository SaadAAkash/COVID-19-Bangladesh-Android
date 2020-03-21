package ninja.saad.palaocorona.data.testyourself

import io.reactivex.Single
import ninja.saad.palaocorona.data.testyourself.model.Questionnaire
import javax.inject.Inject

class TestYourselfRepositoryImpl @Inject constructor(val networkDataSource: TestYourselfDataSource): TestYourselfRepository {
    
    override fun getQuestionnaire(): Single<MutableList<Questionnaire>> {
        return networkDataSource.getQuestionnaire()
    }
}