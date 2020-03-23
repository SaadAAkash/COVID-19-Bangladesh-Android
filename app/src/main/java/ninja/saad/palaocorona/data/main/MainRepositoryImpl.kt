package ninja.saad.palaocorona.data.main

import ninja.saad.palaocorona.data.authentication.AuthenticationDataSource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val authenticationDataSource: AuthenticationDataSource): MainRepository {
    
    override fun isLoggedIn(): Boolean {
        return authenticationDataSource.isUserLoggedIn()
    }
}