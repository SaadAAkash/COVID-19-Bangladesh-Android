package xyz.palaocorona.base.ui

import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val loader = MutableLiveData<Boolean>()
    val toastMessage = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}