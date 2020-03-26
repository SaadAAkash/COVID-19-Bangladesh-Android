package xyz.palaocorona.data.authentication

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import xyz.palaocorona.data.authentication.model.User

interface AuthenticationRepository {
    
    fun sendOtp(phoneNumber: String): Single<String>
    fun verifyOtp(verificationId: String, otp: String): Maybe<User>
    fun saveProfile(name: String, age: String, gender: String, phoneNumber: String): Completable
    fun getUser(): User
    fun logout()
}