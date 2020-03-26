package xyz.palaocorona.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.palaocorona.di.annotations.PerActivity
import xyz.palaocorona.ui.features.authentication.AuthenticationActivity
import xyz.palaocorona.ui.features.main.MainActivity

@Module
abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
    
    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindAuthenticationActivity(): AuthenticationActivity

}