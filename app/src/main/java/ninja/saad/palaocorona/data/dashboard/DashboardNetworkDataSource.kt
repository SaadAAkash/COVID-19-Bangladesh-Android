package ninja.saad.palaocorona.data.dashboard

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class DashboardNetworkDataSource @Inject constructor() {
    
    fun isUserLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().uid != null
    }
}