package ninja.saad.palaocorona.data.main

import io.reactivex.Single
import ninja.saad.palaocorona.BuildConfig
import ninja.saad.palaocorona.data.authentication.AuthenticationDataSource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val authenticationDataSource: AuthenticationDataSource,
                                             private val mainNetworkDataSource: MainNetworkDataSource ): MainRepository {
    
    override fun isLoggedIn(): Boolean {
        return authenticationDataSource.isUserLoggedIn()
    }
    
    override fun checkForUpdate(): Single<Boolean> {
        return mainNetworkDataSource.checkForUpdate(BuildConfig.VERSION_NAME)
    }
}