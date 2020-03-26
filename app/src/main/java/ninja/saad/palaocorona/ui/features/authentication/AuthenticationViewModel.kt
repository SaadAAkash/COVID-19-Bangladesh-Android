package ninja.saad.palaocorona.ui.features.authentication

import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseNetworkException
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.data.network.RetrofitException
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.authentication.AuthenticationRepository
import ninja.saad.palaocorona.data.authentication.model.User
import ninja.saad.palaocorona.util.SingleLiveEvent
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(private val repository: AuthenticationRepository): BaseViewModel() {
    
    var verificationId = SingleLiveEvent<String>()
    var userExists = MutableLiveData<Boolean>()
    var profileSaved = MutableLiveData<Boolean>()
    var phoneNumberInvalid = SingleLiveEvent<Boolean>()
    var otpInvalid = SingleLiveEvent<Boolean>()
    var noInternetConnection = MutableLiveData<Boolean>()
    var user = MutableLiveData<User>()
    var nameInvalide = SingleLiveEvent<Boolean>()
    var ageInvalide = SingleLiveEvent<Boolean>()
    var genderInvalid = SingleLiveEvent<Boolean>()
    
    fun sendOtp(text: String) {
        if(text.isNotEmpty() && text.length == 11 && text.startsWith("01")) {
            var phoneNumber = "+88$text"
            val disposable = repository.sendOtp(phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loader.value = true }
                .doAfterTerminate { loader.value = false }
                .subscribe({
                    Logger.d(it)
                    verificationId.value = it
                }, {
                    if(it is FirebaseNetworkException) {
                        noInternetConnection.value = true
                    }
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        } else {
            phoneNumberInvalid.value = true
        }
    }
    
    fun verifyOtp(verificationId: String?, otp: String) {
        if(otp.isNotEmpty() && verificationId != null && otp.length == 6) {
            val disposable = repository.verifyOtp(verificationId, otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loader.value = true }
                .doAfterTerminate { loader.value = false }
                .subscribe({
                    Logger.d(it)
                    userExists.value = it.name.isNotEmpty()
                }, {
                    if(it is FirebaseNetworkException) {
                        noInternetConnection.value = true
                    } else if(!it.localizedMessage.contains("otp", true)) {
                        userExists.value = false
                    }
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        } else {
            otpInvalid.value = true
        }
    }
    
    fun saveProfile(name: String, age: String, gender: String, phoneNumber: String) {
        if(name.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty()) {
            
            if(phoneNumber.isEmpty() ||
                (phoneNumber.isNotEmpty() && phoneNumber.length == 11 && phoneNumber.startsWith("01"))) {
    
                val disposable = repository.saveProfile(name, age, gender, phoneNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { loader.value = true }
                    .doAfterTerminate { loader.value = false }
                    .subscribe({
                        profileSaved.value = true
                    }, {
                        if (it is FirebaseNetworkException) {
                            noInternetConnection.value = true
                        }
                        profileSaved.value = false
                        it.printStackTrace()
                    })
                compositeDisposable.add(disposable)
            } else {
                phoneNumberInvalid.value = true
            }
        } else {
            if(name.isEmpty()) nameInvalide.value= true
            if(age.isEmpty()) ageInvalide.value= true
            if(gender.isEmpty()) genderInvalid.value= true
        }
    }
    
    fun getProfile() {
        this.user.value = repository.getUser()
    }
    
    fun logout() {
        repository.logout()
    }
}