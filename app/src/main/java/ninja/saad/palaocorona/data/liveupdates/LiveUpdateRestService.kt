package ninja.saad.palaocorona.data.liveupdates

import io.reactivex.Single
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateResponse
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateResponseDGHS
import retrofit2.Response
import retrofit2.http.GET

interface LiveUpdateRestService {
    @GET("https://covid19.mathdro.id/api/countries/Bangladesh")
    fun getLiveUpdate(): Single<Response<LiveUpdateResponse>>
    
    @GET("http://api.palaocorona.xyz/api/v1/dghs/data")
    fun getLiveUpdateDGHS(): Single<Response<LiveUpdateResponseDGHS>>
}