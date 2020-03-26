package xyz.palaocorona.ui.features.liveupdates

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import xyz.palaocorona.base.ui.BaseViewModel
import xyz.palaocorona.data.liveupdates.LiveUpdateRepository
import xyz.palaocorona.data.liveupdates.model.LiveUpdateDataModelDGHS
import xyz.palaocorona.data.liveupdates.model.LiveUpdateResponse
import javax.inject.Inject

class LiveUpdatesViewModel @Inject constructor(val repository: LiveUpdateRepository): BaseViewModel() {
    
    var liveUpdates = MutableLiveData<LiveUpdateResponse>()
    var liveUpdatesDGHS = MutableLiveData<LiveUpdateDataModelDGHS>()
    
    fun getLiveUpdates() {
        if(liveUpdates.value == null) {
            val disposable = repository.getLiveUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loader.value = true }
                .doAfterTerminate { loader.value = false }
                .subscribe({
                    Logger.d(it)
                    liveUpdates.value = it
                }, {
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        }
    }
    
    fun getLiveUpdatesDGHS() {
        if(liveUpdates.value == null) {
            val disposable = repository.getLiveUpdateDGHS()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loader.value = true }
                .doAfterTerminate { loader.value = false }
                .subscribe({
                    Logger.d(it)
                    liveUpdatesDGHS.value = it
                }, {
                    it.printStackTrace()
                })
            compositeDisposable.add(disposable)
        }
    }
}