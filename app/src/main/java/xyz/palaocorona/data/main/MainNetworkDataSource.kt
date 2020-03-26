package xyz.palaocorona.data.main

import io.reactivex.Single
import xyz.palaocorona.base.data.network.onResponse
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