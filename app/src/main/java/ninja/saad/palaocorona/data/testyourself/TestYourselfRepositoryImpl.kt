package ninja.saad.palaocorona.data.testyourself

import io.reactivex.Completable
import io.reactivex.Single
import ninja.saad.palaocorona.data.testyourself.model.Question
import ninja.saad.palaocorona.util.VIEW_TYPE
import javax.inject.Inject

class TestYourselfRepositoryImpl @Inject constructor(val networkDataSource: TestYourselfDataSource): TestYourselfRepository {
    
    override fun getQuestionnaire(): Single<MutableList<Question>> {
        return networkDataSource.getQuestionnaire()
    }
    
    override fun setData(questions: MutableList<Question>): Completable {
        val answers = HashMap<String, Any>()
        questions.forEach {
            if(it.viewType != VIEW_TYPE.CHECKBOX.value) {
                answers.put(it.title, it.selectedAnswer)
            } else {
                answers.put(it.title, it.isChecked)
            }
        }
        return networkDataSource.setResult(answers)
    }
}