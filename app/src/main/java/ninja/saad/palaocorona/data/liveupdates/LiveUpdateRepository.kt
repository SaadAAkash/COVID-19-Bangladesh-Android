package ninja.saad.palaocorona.data.liveupdates

import io.reactivex.Single
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateResponse

interface LiveUpdateRepository {
    
    fun getLiveUpdate(): Single<LiveUpdateResponse>
}