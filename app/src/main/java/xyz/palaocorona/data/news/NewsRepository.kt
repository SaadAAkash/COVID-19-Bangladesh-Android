package xyz.palaocorona.data.news

import io.reactivex.Single
import xyz.palaocorona.data.news.model.News

interface NewsRepository {
    
    fun getNews(language: String, page: Int): Single<MutableList<News>>
}