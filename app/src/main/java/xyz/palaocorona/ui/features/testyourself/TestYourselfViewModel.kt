package xyz.palaocorona.ui.features.testyourself

import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseNetworkException
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import xyz.palaocorona.base.data.network.RetrofitException
import xyz.palaocorona.base.ui.BaseViewModel
import xyz.palaocorona.data.testyourself.TestYourselfRepository
import xyz.palaocorona.data.testyourself.model.LocaleData
import xyz.palaocorona.data.testyourself.model.Question
import xyz.palaocorona.util.SingleLiveEvent
import javax.inject.Inject

class TestYourselfViewModel @Inject constructor(private val repository: TestYourselfRepository): BaseViewModel() {
    
    var questionnaire = SingleLiveEvent<MutableList<Question>>()
    var currentIndex = 0
    var formNotCompleted = SingleLiveEvent<Boolean>()
    var noInternetConnection = MutableLiveData<Boolean>()
    var formSubmitted = SingleLiveEvent<Boolean>()
    var result = MutableLiveData<Int>()
    private var allQuestionnaire = mutableListOf<Question>()
    
    fun getQuestionnaire() {
        if(allQuestionnaire.size == 0) {
            val disposable = repository.getQuestionnaire()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loader.value = true }
                .doAfterTerminate { loader.value = false }
                .subscribe({
                    this.allQuestionnaire = it
                    this.questionnaire.value =
                        allQuestionnaire
                }, {
                    if(it is RetrofitException && it.getKind() == RetrofitException.Kind.NETWORK) {
                        noInternetConnection.value = true
                    }
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        } else {
            this.questionnaire.value =
                allQuestionnaire.apply {
                    forEach {
                        it.selectedAnswer = LocaleData()
                    }
                }
        }
        
    }
    
    fun setAnswer(question: Question, answer: LocaleData) {
        allQuestionnaire[allQuestionnaire.indexOf(question)] = question.apply {
            val position = texts.indexOf(answer)
            if(question.singleSelection == true) {
                selectedAnswer = texts[position]
                selectedAnswerPosition = position
            } else {
                if(!selectedAnswer.englishText.contains(answer.englishText)) {
                    selectedAnswer.englishText += "${if(selectedAnswer.englishText.length > 0) ", " else ""}${answer.englishText}"
                }
                if(!selectedAnswer.banglaText.contains(answer.banglaText)) {
                    selectedAnswer.banglaText += "${if(selectedAnswer.banglaText.length > 0) ", " else ""}${answer.banglaText}"
                }
            }
        }
    }
    
    fun removeAnswer(question: Question, answer: LocaleData) {
        allQuestionnaire[allQuestionnaire.indexOf(question)] = allQuestionnaire[allQuestionnaire.indexOf(question)].apply {
            
            if(selectedAnswer.englishText.contains(answer.englishText)) {
                try {
                    var oldValue =
                        if (selectedAnswer.englishText.indexOf(answer.englishText) != 0) ", " + answer.englishText else answer.englishText + ", "
                    selectedAnswer.englishText = selectedAnswer.englishText.removeRange(
                        selectedAnswer.englishText.indexOf(oldValue),
                        selectedAnswer.englishText.indexOf(oldValue) + oldValue.length
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if(selectedAnswer.banglaText.contains(answer.banglaText)) {
                try {
                    selectedAnswer.banglaText = selectedAnswer.banglaText.replace(
                        "${if (selectedAnswer.banglaText.indexOf(answer.banglaText) != 0) ", " else ""}${answer.banglaText}",
                        ""
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
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
            selectedAnswer = LocaleData(text)
        }
    }
    
    fun setResult() {
        var notCompleted = false
        allQuestionnaire.forEach {
            if(it.selectedAnswer.englishText.isEmpty()) notCompleted = true
        }
        
        if(!notCompleted) {
            val disposable = repository.setData(allQuestionnaire)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    formSubmitted.value = true
                    Logger.d("Success")
                }, {
                    if(it is FirebaseNetworkException) {
                        noInternetConnection.value = true
                    }
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        } else {
            formNotCompleted.value = true
        }
    }
    
    fun getResult() {
        var level = allQuestionnaire.size.toDouble()
        allQuestionnaire.forEach {
            if(it.selectedAnswer.englishText.contains("yes", true) ||
                (!it.singleSelection && !it.selectedAnswer.englishText.contains("none", true))) {
                level += it.level.toDouble()
            }
        }
        this.result.value = (level / allQuestionnaire.size).toInt()
    }
}