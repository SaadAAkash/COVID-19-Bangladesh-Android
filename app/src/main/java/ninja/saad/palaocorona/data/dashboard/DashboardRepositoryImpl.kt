package ninja.saad.palaocorona.data.dashboard

import android.content.Context
import io.reactivex.Single
import ninja.saad.palaocorona.util.ConnectivityManager
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val context: Context,
    private val dashboardNetworkDataSource: DashboardNetworkDataSource) : DashboardRepository {
    
    override fun isUserLoggedIn(): Boolean {
        return dashboardNetworkDataSource.isUserLoggedIn()
    }
    
    override fun getSliderImages(): Single<MutableList<String>> {
        return dashboardNetworkDataSource.getSliderImages()
    }
    
    override fun isInternetAvailable(): Boolean {
        return ConnectivityManager.isConnected(context)
    }
}