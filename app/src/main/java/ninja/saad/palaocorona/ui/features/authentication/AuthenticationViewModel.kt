package ninja.saad.palaocorona.ui.features.authentication

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.authentication.AuthenticationRepository
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(private val repository: AuthenticationRepository): BaseViewModel() {
    
    var verificationId = MutableLiveData<String>()
    var userExists = MutableLiveData<Boolean>()
    var profileSaved = MutableLiveData<Boolean>()
    
    fun sendOtp(text: String) {
        if(text.isNotEmpty()) {
            var phoneNumber = if(text.startsWith("+")) text else "+88$text"
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
        }
    }
    
    fun verifyOtp(verificationId: String?, otp: String) {
        if(otp.isNotEmpty() && verificationId != null) {
            val disposable = repository.verifyOtp(verificationId, otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userExists.value = it.name.isNotEmpty()
                }, {
                    userExists.value = false
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
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