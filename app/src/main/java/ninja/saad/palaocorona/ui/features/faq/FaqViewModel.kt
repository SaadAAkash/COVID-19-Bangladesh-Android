package ninja.saad.palaocorona.ui.features.faq

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.faq.Faq
import ninja.saad.palaocorona.data.faq.FaqRepository
import javax.inject.Inject

class FaqViewModel @Inject constructor(private val repo: FaqRepository): BaseViewModel() {
    
    var faq = MutableLiveData<MutableList<Faq>>()
    
    fun getFaq() {
        val disposable = repo.getFaq()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.faq.value = it
            }, {
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }
}