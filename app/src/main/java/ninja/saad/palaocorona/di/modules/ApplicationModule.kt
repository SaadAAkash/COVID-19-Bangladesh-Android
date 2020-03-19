package ninja.saad.palaocorona.di.modules

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ninja.saad.palaocorona.base.data.local.AppPreference
import ninja.saad.palaocorona.base.data.local.AppPreferenceImpl
import ninja.saad.palaocorona.util.AppViewModelFactory

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindAppContext(application: Application): Context

    @Binds
    abstract fun bindVMFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
    
    @Binds
    abstract fun provideAppPreference(preference: AppPreferenceImpl): AppPreference
    
}