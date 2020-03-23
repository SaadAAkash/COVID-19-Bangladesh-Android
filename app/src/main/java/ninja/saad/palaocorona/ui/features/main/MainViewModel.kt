package ninja.saad.palaocorona.ui.features.main

import androidx.lifecycle.MutableLiveData
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.main.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(val mainRepository: MainRepository): BaseViewModel() {
    val isLoggedIn = MutableLiveData<Boolean>()
    
    fun getIsLoggedIn() {
        isLoggedIn.value = mainRepository.isLoggedIn()
    }
}