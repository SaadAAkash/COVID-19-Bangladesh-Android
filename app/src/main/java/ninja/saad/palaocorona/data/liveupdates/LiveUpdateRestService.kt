package ninja.saad.palaocorona.data.liveupdates

import io.reactivex.Single
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateResponse
import retrofit2.Response
import retrofit2.http.GET

interface LiveUpdateRestService {
    @GET("https://covid19.mathdro.id/api/countries/Bangladesh")
    fun getLiveUpdate(): Single<Response<LiveUpdateResponse>>
}