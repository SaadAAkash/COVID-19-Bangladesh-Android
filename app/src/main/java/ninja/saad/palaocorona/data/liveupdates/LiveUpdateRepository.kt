package ninja.saad.palaocorona.data.liveupdates

import io.reactivex.Single
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateDataModelDGHS
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateResponse
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateResponseDGHS

interface LiveUpdateRepository {
    
    fun getLiveUpdate(): Single<LiveUpdateResponse>
    
    fun getLiveUpdateDGHS(): Single<LiveUpdateDataModelDGHS>
    
}