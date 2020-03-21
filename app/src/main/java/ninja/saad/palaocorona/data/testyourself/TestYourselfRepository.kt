package ninja.saad.palaocorona.data.testyourself

import io.reactivex.Single
import ninja.saad.palaocorona.data.testyourself.model.Questionnaire

interface TestYourselfRepository {
    
    fun getQuestionnaire(): Single<MutableList<Questionnaire>>
}