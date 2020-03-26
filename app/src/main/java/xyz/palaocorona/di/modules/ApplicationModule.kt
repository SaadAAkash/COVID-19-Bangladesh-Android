package xyz.palaocorona.di.modules

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import xyz.palaocorona.base.data.local.AppPreference
import xyz.palaocorona.base.data.local.AppPreferenceImpl
import xyz.palaocorona.base.data.network.NetworkFactory
import xyz.palaocorona.data.liveupdates.LiveUpdateRestService
import xyz.palaocorona.data.main.MainRestService
import xyz.palaocorona.data.news.NewsRestService
import xyz.palaocorona.data.testyourself.TestYourselfRestService
import xyz.palaocorona.util.AppViewModelFactory
import javax.inject.Singleton

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindAppContext(application: Application): Context

    @Binds
    abstract fun bindVMFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
    
    @Binds
    abstract fun provideAppPreference(preference: AppPreferenceImpl): AppPreference
    
    @Module
    companion object {
        
        @Provides
        @Singleton
        @JvmStatic
        fun provideTestYourselfRestService(context: Context): TestYourselfRestService {
            return NetworkFactory.createService(context, TestYourselfRestService::class.java)
        }
        
        @Provides
        @Singleton
        @JvmStatic
        fun provideNewsRestService(context: Context): NewsRestService {
            return NetworkFactory.createService(context, NewsRestService::class.java)
        }
    
        @Provides
        @Singleton
        @JvmStatic
        fun provideLiveUpdateRestService(context: Context): LiveUpdateRestService {
            return NetworkFactory.createService(context, LiveUpdateRestService::class.java)
        }
    
        @Provides
        @Singleton
        @JvmStatic
        fun provideMainRestService(context: Context): MainRestService {
            return NetworkFactory.createService(context, MainRestService::class.java)
        }
    }
    
}