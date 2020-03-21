package ninja.saad.palaocorona.ui.features.authentication

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.authentication.AuthenticationRepository
import ninja.saad.palaocorona.util.SingleLiveEvent
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(private val repository: AuthenticationRepository): BaseViewModel() {
    
    var verificationId = SingleLiveEvent<String>()
    var userExists = MutableLiveData<Boolean>()
    var profileSaved = MutableLiveData<Boolean>()
    var phoneNumberInvalid = SingleLiveEvent<Boolean>()
    var otpInvalid = SingleLiveEvent<Boolean>()
    
    fun sendOtp(text: String) {
        if(text.isNotEmpty() && text.length == 11 && text.startsWith("01")) {
            var phoneNumber = "+88$text"
            val disposable = repository.sendOtp(phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(it)
                    verificationId.value = it
                }, {
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
                .subscribe({
                    Logger.d(it)
                    userExists.value = it.name.isNotEmpty()
                }, {
                    if(!it.localizedMessage.contains("otp", true)) {
                        userExists.value = false
                    }
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        } else {
            otpInvalid.value = true
        }
    }
    
    fun saveProfile(name: String, age: String, gender: Int, phoneNumber: String) {
        val disposable = repository.saveProfile(name, age, gender, phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                profileSaved.value = true
            }, {
                profileSaved.value = false
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }
}