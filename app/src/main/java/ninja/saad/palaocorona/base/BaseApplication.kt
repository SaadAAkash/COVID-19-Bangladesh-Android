package ninja.saad.palaocorona.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ninja.saad.palaocorona.di.components.DaggerAppComponent
import javax.inject.Inject

class BaseApplication : DaggerApplication(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}