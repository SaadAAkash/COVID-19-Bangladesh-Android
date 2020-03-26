package xyz.palaocorona.data.dashboard

import android.content.Context
import io.reactivex.Single
import xyz.palaocorona.base.data.local.AppPreference
import xyz.palaocorona.util.ConnectivityManager
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val context: Context,
    private val preference: AppPreference,
    private val dashboardNetworkDataSource: DashboardNetworkDataSource) : DashboardRepository {
    
    override fun isUserLoggedIn(): Boolean {
        return dashboardNetworkDataSource.isUserLoggedIn() && preference.user.name.isNotEmpty()
    }
    
    override fun getSliderImages(): Single<MutableList<String>> {
        return dashboardNetworkDataSource.getSliderImages()
    }
    
    override fun isInternetAvailable(): Boolean {
        return ConnectivityManager.isConnected(context)
    }
}