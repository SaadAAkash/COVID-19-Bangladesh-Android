package xyz.palaocorona.data.main

import io.reactivex.Single
import xyz.palaocorona.BuildConfig
import xyz.palaocorona.data.authentication.AuthenticationDataSource
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