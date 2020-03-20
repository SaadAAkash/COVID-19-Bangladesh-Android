package ninja.saad.palaocorona.ui.features.news

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.news.News
import javax.inject.Inject

class NewsViewModel @Inject constructor(): BaseViewModel() {
    
    var news = MutableLiveData<MutableList<News>>()
    
    /*fun getNews() {
        val disposable = newsRepo.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.news.value = it
            }, {
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }*/
}