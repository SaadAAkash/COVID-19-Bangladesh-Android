package ninja.saad.palaocorona.data.news

import io.reactivex.Single
import ninja.saad.palaocorona.base.data.network.onResponse
import ninja.saad.palaocorona.data.news.model.NewsResponse
import javax.inject.Inject

class NewsDataSource @Inject constructor(private val newsNetworkService: NewsRestService) {
    
    fun getNews(language: String, page: Int): Single<NewsResponse> {
        return newsNetworkService.getNews(language, page)
            .onResponse()
    }
}