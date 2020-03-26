package ninja.saad.palaocorona.data.main

import io.reactivex.Single
import ninja.saad.palaocorona.data.main.model.AppUpdateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainRestService {
    
    @GET("app/version/check")
    fun checkForUpdate(@Query("version") versionName: String): Single<Response<AppUpdateResponse>>
}