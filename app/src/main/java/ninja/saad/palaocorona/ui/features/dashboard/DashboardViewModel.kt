package ninja.saad.palaocorona.ui.features.dashboard

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.dashboard.DashboardRepository
import javax.inject.Inject

class DashboardViewModel @Inject constructor(val repository: DashboardRepository): BaseViewModel() {
    
    var sliderImages = MutableLiveData<MutableList<String>>()
    
    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }
    
    fun getSliderImages() {
        if(sliderImages.value == null) {
            val disposable = repository.getSliderImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(it)
                    sliderImages.value = it
                }, {
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        }
    }
}