package ninja.saad.palaocorona.ui.features.main

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.main.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(val mainRepository: MainRepository): BaseViewModel() {
    val isLoggedIn = MutableLiveData<Boolean>()
    val isUpdateAvailable = MutableLiveData<Boolean>()
    
    fun getIsLoggedIn() {
        isLoggedIn.value = mainRepository.isLoggedIn()
    }
    
    fun checkForUpdate() {
        val disposable = mainRepository.checkForUpdate()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isUpdateAvailable.value = !it
            }, {
                it.printStackTrace()
            })
    }
}