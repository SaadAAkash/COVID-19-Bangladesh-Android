package xyz.palaocorona.data.liveupdates

import io.reactivex.Single
import xyz.palaocorona.data.liveupdates.model.LiveUpdateDataModelDGHS
import xyz.palaocorona.data.liveupdates.model.LiveUpdateResponse
import javax.inject.Inject

class LiveUpdateRepositoryImpl @Inject constructor(private val networkDataSource: LiveUpdateDataSource): LiveUpdateRepository {
    
    override fun getLiveUpdate(): Single<LiveUpdateResponse> {
        return networkDataSource.getLiveUpdate()
            .map {
                it
            }
    }
    
    override fun getLiveUpdateDGHS(): Single<LiveUpdateDataModelDGHS> {
        return networkDataSource.getLiveUpdateDGHS()
            .map {
                it.data
            }
    }
}