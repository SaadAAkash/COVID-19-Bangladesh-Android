package ninja.saad.palaocorona.data.main

import io.reactivex.Single
import ninja.saad.palaocorona.base.data.network.onResponse
import javax.inject.Inject

class MainNetworkDataSource @Inject constructor(private val mainRestService: MainRestService) {
    
    fun checkForUpdate(versionName: String): Single<Boolean> {
        return mainRestService.checkForUpdate(versionName)
            .onResponse()
            .map {
                it.data
            }
    }
}