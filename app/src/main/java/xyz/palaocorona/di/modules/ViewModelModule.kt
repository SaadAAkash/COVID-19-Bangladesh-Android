package xyz.palaocorona.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import xyz.palaocorona.di.annotations.ViewModelKey
import xyz.palaocorona.ui.features.authentication.AuthenticationViewModel
import xyz.palaocorona.ui.features.contact.ContactViewModel
import xyz.palaocorona.ui.features.dashboard.DashboardViewModel
import xyz.palaocorona.ui.features.faq.FaqViewModel
import xyz.palaocorona.ui.features.liveupdates.LiveUpdatesViewModel
import xyz.palaocorona.ui.features.main.MainViewModel
import xyz.palaocorona.ui.features.news.NewsViewModel
import xyz.palaocorona.ui.features.testyourself.TestYourselfViewModel

@Module
abstract class ViewModelModule {
    
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(FaqViewModel::class)
    abstract fun bindFaqViewModel(viewModel: FaqViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel::class)
    abstract fun bindAuthenticationViewModel(viewModel: AuthenticationViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(TestYourselfViewModel::class)
    abstract fun bindTestYourselfViewModel(viewModel: TestYourselfViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(LiveUpdatesViewModel::class)
    abstract fun bindLiveUpdatesViewModel(viewModel: LiveUpdatesViewModel): ViewModel
    
    @Binds
    @IntoMap
    @ViewModelKey(ContactViewModel::class)
    abstract fun bindContactViewModel(viewModel: ContactViewModel): ViewModel
}