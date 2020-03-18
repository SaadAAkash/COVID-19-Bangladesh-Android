package ninja.saad.palaocorona.di.modules

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ninja.saad.palaocorona.di.annotations.ViewModelKey
import ninja.saad.palaocorona.ui.features.dashboard.MainActivity
import ninja.saad.palaocorona.util.AppViewModelFactory

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindAppContext(application: Application): Context

    @Binds
    abstract fun bindVMFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    /*@Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: MainActivityViewModel): ViewModel*/

}