package ninja.saad.palaocorona.data.dashboard

import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val dashboardNetworkDataSource: DashboardNetworkDataSource) : DashboardRepository {
    
    override fun isUserLoggedIn(): Boolean {
        return dashboardNetworkDataSource.isUserLoggedIn()
    }
}