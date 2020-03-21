package ninja.saad.palaocorona.ui.features.testyourself

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.testyourself.TestYourselfRepository
import ninja.saad.palaocorona.data.testyourself.model.Questionnaire
import javax.inject.Inject

class TestYourselfViewModel @Inject constructor(private val repository: TestYourselfRepository): BaseViewModel() {
    
    var questionnaire = MutableLiveData<MutableList<Questionnaire>>()
    
    fun getQuestionnaire() {
        val disposable = repository.getQuestionnaire()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.questionnaire.value = it
            }, {
                it.printStackTrace()
            })
        
    }
}