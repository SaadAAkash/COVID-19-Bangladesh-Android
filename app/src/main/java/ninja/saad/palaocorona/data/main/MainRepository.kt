package ninja.saad.palaocorona.data.main

import io.reactivex.Single

interface MainRepository {
    
    fun isLoggedIn(): Boolean
    fun checkForUpdate(): Single<Boolean>
}