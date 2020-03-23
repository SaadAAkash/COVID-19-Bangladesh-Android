package ninja.saad.palaocorona.data.news

import io.reactivex.Single
import ninja.saad.palaocorona.data.news.model.News

interface NewsRepository {
    
    fun getNews(language: String, page: Int): Single<MutableList<News>>
}