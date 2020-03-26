package ninja.saad.palaocorona.base

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.messaging.FirebaseMessaging
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ninja.saad.palaocorona.base.ui.LocaleManager
import ninja.saad.palaocorona.di.components.DaggerAppComponent

class BaseApplication : DaggerApplication(), LifecycleObserver {
    
    companion object {
        lateinit var localeManager: LocaleManager
    }
    
    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager.setLocale(base))
    }
    
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }
    
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}