package ninja.saad.palaocorona.ui.features.news

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.news.NewsRepository
import ninja.saad.palaocorona.data.news.model.News
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository): BaseViewModel() {
    fun getNews(currentLanguage: String) {
    
    }
    
    var news = MutableLiveData<MutableList<News>>()
    
    fun getNews(language: String, visibleItemCount: Int) {
        val disposable = newsRepository.getNews(language, (visibleItemCount / 10) + 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.news.value = it
            }, {
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }
}