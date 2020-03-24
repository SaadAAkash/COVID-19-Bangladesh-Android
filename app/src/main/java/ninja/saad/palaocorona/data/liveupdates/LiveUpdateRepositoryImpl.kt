package ninja.saad.palaocorona.data.liveupdates

import io.reactivex.Single
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateDataModel
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateResponse
import javax.inject.Inject

class LiveUpdateRepositoryImpl @Inject constructor(private val networkDataSource: LiveUpdateDataSource): LiveUpdateRepository {
    
    override fun getLiveUpdate(): Single<LiveUpdateResponse> {
        return networkDataSource.getLiveUpdate()
            .map {
                it
            }
    }
}