package ninja.saad.palaocorona.data.dashboard

import io.reactivex.Single
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val dashboardNetworkDataSource: DashboardNetworkDataSource) : DashboardRepository {
    
    override fun isUserLoggedIn(): Boolean {
        return dashboardNetworkDataSource.isUserLoggedIn()
    }
    
    override fun getSliderImages(): Single<MutableList<String>> {
        return dashboardNetworkDataSource.getSliderImages()
    }
}