package ninja.saad.palaocorona.base

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.franmontiel.localechanger.LocaleChanger
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ninja.saad.palaocorona.di.components.DaggerAppComponent
import java.util.*
import javax.inject.Inject

class BaseApplication : DaggerApplication(), LifecycleObserver {
    
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(this)
        LocaleChanger.initialize(applicationContext, mutableListOf(Locale("en"), Locale("bn")));
        Logger.addLogAdapter(AndroidLogAdapter())
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleChanger.onConfigurationChanged()
    }
    
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}