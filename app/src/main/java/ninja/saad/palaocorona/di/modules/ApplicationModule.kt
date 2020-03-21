package ninja.saad.palaocorona.di.modules

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import ninja.saad.palaocorona.base.data.local.AppPreference
import ninja.saad.palaocorona.base.data.local.AppPreferenceImpl
import ninja.saad.palaocorona.base.data.network.NetworkFactory
import ninja.saad.palaocorona.data.testyourself.TestYourselfRestService
import ninja.saad.palaocorona.util.AppViewModelFactory
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
    }
    
}