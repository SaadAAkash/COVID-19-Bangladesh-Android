package xyz.palaocorona.data.liveupdates

import io.reactivex.Single
import xyz.palaocorona.base.data.network.onResponse
import xyz.palaocorona.data.liveupdates.model.LiveUpdateResponse
import xyz.palaocorona.data.liveupdates.model.LiveUpdateResponseDGHS
import javax.inject.Inject

class LiveUpdateDataSource @Inject constructor(private val liveUpdateNetworkService: LiveUpdateRestService) {
    
    fun getLiveUpdate(): Single<LiveUpdateResponse> {
        return liveUpdateNetworkService.getLiveUpdate()
            .onResponse()
    }
    
    fun getLiveUpdateDGHS(): Single<LiveUpdateResponseDGHS> {
        return liveUpdateNetworkService.getLiveUpdateDGHS()
            .onResponse()
    }
}