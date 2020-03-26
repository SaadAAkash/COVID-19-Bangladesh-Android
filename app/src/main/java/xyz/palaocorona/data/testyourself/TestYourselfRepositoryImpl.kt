package xyz.palaocorona.data.testyourself

import io.reactivex.Completable
import io.reactivex.Single
import xyz.palaocorona.data.testyourself.model.LocaleData
import xyz.palaocorona.data.testyourself.model.Question
import xyz.palaocorona.util.VIEW_TYPE
import javax.inject.Inject

class TestYourselfRepositoryImpl @Inject constructor(val networkDataSource: TestYourselfDataSource): TestYourselfRepository {
    
    override fun getQuestionnaire(): Single<MutableList<Question>> {
        return networkDataSource.getQuestionnaire()
    }
    
    override fun setData(questions: MutableList<Question>): Completable {
        val answers = mutableListOf<MutableList<LocaleData>>()
        questions.forEach {
            if(it.viewType != VIEW_TYPE.CHECKBOX.value) {
                answers.add(mutableListOf(it.title, it.selectedAnswer))
            } else {
                answers.add(mutableListOf(it.title, LocaleData(it.isChecked.toString(), it.isChecked.toString())))
            }
        }
        return networkDataSource.setResult(answers)
    }
}