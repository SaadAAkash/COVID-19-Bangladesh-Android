package ninja.saad.palaocorona.data.news

import io.reactivex.Single
import ninja.saad.palaocorona.data.news.model.News
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val networkDataSource: NewsDataSource): NewsRepository {
    
    override fun getNews(language: String, page: Int): Single<MutableList<News>> {
        return networkDataSource.getNews(language, page)
            .map {
                it.data
            }
    }
}