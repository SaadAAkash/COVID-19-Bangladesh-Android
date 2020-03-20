package ninja.saad.palaocorona.data.authentication

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ninja.saad.palaocorona.base.data.local.AppPreference
import ninja.saad.palaocorona.data.authentication.model.User
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
    private val preference: AppPreference): AuthenticationRepository {
    
    override fun sendOtp(phoneNumber: String): Single<String> {
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
        gender: Int,
        phoneNumber: String
    ): Completable {
        val user = User(name, age, gender.toLong(), phoneNumber)
        return authenticationDataSource.saveProfile(user)
    }
}