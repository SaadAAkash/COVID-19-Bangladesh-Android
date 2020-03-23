package ninja.saad.palaocorona.data.testyourself

import io.reactivex.Completable
import io.reactivex.Single
import ninja.saad.palaocorona.data.testyourself.model.Question

interface TestYourselfRepository {
    
    fun getQuestionnaire(): Single<MutableList<Question>>
    fun setData(questions: MutableList<Question>): Completable
}