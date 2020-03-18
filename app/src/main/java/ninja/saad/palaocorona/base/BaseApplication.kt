package ninja.saad.palaocorona.base

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ninja.saad.palaocorona.di.components.DaggerAppComponent
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector, LifecycleObserver {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}