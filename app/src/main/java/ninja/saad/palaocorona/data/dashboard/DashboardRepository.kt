package ninja.saad.palaocorona.data.dashboard

import io.reactivex.Single

interface DashboardRepository {
    
    fun isUserLoggedIn(): Boolean
    
    fun getSliderImages(): Single<MutableList<String>>
}