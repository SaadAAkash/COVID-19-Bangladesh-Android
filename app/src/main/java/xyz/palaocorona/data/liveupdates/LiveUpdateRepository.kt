package xyz.palaocorona.data.liveupdates

import io.reactivex.Single
import xyz.palaocorona.data.liveupdates.model.LiveUpdateDataModelDGHS
import xyz.palaocorona.data.liveupdates.model.LiveUpdateResponse

interface LiveUpdateRepository {
    
    fun getLiveUpdate(): Single<LiveUpdateResponse>
    
    fun getLiveUpdateDGHS(): Single<LiveUpdateDataModelDGHS>
    
}