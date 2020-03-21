package ninja.saad.palaocorona.ui.features.dashboard

import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.dashboard.DashboardRepository
import javax.inject.Inject

class DashboardViewModel @Inject constructor(val repository: DashboardRepository): BaseViewModel() {
    
    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }
}