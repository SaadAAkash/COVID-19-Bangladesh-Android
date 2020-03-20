package ninja.saad.palaocorona.data.authentication

import io.reactivex.Observable
import io.reactivex.Single

interface AuthenticationRepository {
    
    fun sendOtp(phoneNumber: String): Single<String>
    fun verifyOtp(verificationId: String, otp: String): Single<Boolean>
}