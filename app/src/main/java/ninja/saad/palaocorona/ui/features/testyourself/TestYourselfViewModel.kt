package ninja.saad.palaocorona.ui.features.testyourself

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.testyourself.TestYourselfRepository
import ninja.saad.palaocorona.data.testyourself.model.Question
import ninja.saad.palaocorona.util.SingleLiveEvent
import java.text.FieldPosition
import javax.inject.Inject

class TestYourselfViewModel @Inject constructor(private val repository: TestYourselfRepository): BaseViewModel() {
    
    var questionnaire = SingleLiveEvent<MutableList<Question>>()
    var currentIndex = 0
    private var allQuestionnaire = mutableListOf<Question>()
    
    fun getQuestionnaire() {
        if(allQuestionnaire.size == 0) {
            val disposable = repository.getQuestionnaire()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.allQuestionnaire = it
                    this.questionnaire.value =
                        allQuestionnaire
                }, {
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        } else {
            this.questionnaire.value =
                allQuestionnaire
        }
        
    }
    
    fun setAnswer(question: Question, answer: String) {
        allQuestionnaire[allQuestionnaire.indexOf(question)] = question.apply {
            val position = texts.indexOf(answer)
            selectedAnswer = texts[position]
            selectedAnswerPosition = position
        }
    }
    
    fun setAnswer(question: Question, position: Int) {
        allQuestionnaire[allQuestionnaire.indexOf(question)] = question.apply {
            selectedAnswer = texts[position]
            selectedAnswerPosition = position
        }
    }
    
    fun setChecked(question: Question, checked: Boolean) {
        allQuestionnaire[allQuestionnaire.indexOf(question)] = question.apply {
            selectedAnswer = title
            isChecked = checked
        }
    }
    
    fun setEditableAnswer(question: Question, text: String) {
        allQuestionnaire[allQuestionnaire.indexOf(question)] = question.apply {
            selectedAnswer = text
        }
    }
}