package ninja.saad.palaocorona.data.authentication

import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val authenticationDataSource: AuthenticationDataSource): AuthenticationRepository {
    
    override fun sendOtp(phoneNumber: String): Single<String> {
        return authenticationDataSource.sendOtp(phoneNumber)
    }
    
    override fun verifyOtp(verificationId: String, otp: String): Single<Boolean> {
        return authenticationDataSource.verifyOtp(verificationId, otp)
    }
}