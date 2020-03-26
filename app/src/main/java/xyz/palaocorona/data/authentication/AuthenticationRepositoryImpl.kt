package xyz.palaocorona.data.authentication

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import xyz.palaocorona.base.data.local.AppPreference
import xyz.palaocorona.data.authentication.model.User
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
    private val preference: AppPreference): AuthenticationRepository {
    
    override fun sendOtp(phoneNumber: String): Observable<String> {
        return authenticationDataSource.sendOtp(phoneNumber)
    }
    
    override fun verifyOtp(verificationId: String, otp: String): Maybe<User> {
        return authenticationDataSource.verifyOtp(verificationId, otp)
            .flatMapMaybe {
                authenticationDataSource.getUserInfo(it)
            }.map {
                preference.user = it
                it
            }
    }
    
    override fun saveProfile(
        name: String,
        age: String,
        gender: String,
        phoneNumber: String
    ): Completable {
        val user = User(name, age, gender, phoneNumber)
        return authenticationDataSource.saveProfile(user)
            .doOnComplete {
                preference.user = user
            }
    }
    
    override fun getUser(): User {
        return preference.user
    }
    
    override fun logout() {
        authenticationDataSource.logout()
    }
    
    override fun isUserExists(): Maybe<User> {
        return authenticationDataSource.getUserInfo(FirebaseAuth.getInstance().uid ?: "")
    }
}