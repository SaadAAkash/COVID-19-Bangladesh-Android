package ninja.saad.palaocorona.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ninja.saad.palaocorona.di.annotations.ViewModelKey
import ninja.saad.palaocorona.ui.features.dashboard.MainViewModel

@Module
abstract class ViewModelModule {
    
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: MainViewModel): ViewModel
}