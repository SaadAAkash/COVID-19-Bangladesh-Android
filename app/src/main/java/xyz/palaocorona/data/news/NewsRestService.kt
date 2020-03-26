package xyz.palaocorona.data.news

import io.reactivex.Single
import xyz.palaocorona.data.news.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsRestService {
    
    @GET("news/fetch/lang/{language}/limit/10/page/{page}")
    fun getNews(@Path("language") language: String, @Path("page") page: Int): Single<Response<NewsResponse>>
}