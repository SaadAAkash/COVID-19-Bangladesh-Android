package ninja.saad.palaocorona.ui.features.liveupdates

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.liveupdates.LiveUpdateRepository
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateDataModelDGHS
import ninja.saad.palaocorona.data.liveupdates.model.LiveUpdateResponse
import javax.inject.Inject

class LiveUpdatesViewModel @Inject constructor(val repository: LiveUpdateRepository): BaseViewModel() {
    
    var liveUpdates = MutableLiveData<LiveUpdateResponse>()
    var liveUpdatesDGHS = MutableLiveData<LiveUpdateDataModelDGHS>()
    
    fun getLiveUpdates() {
        if(liveUpdates.value == null) {
            val disposable = repository.getLiveUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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