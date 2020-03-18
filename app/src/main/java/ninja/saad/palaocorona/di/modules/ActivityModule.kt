package ninja.saad.palaocorona.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ninja.saad.palaocorona.di.annotations.PerActivity
import ninja.saad.palaocorona.ui.features.dashboard.MainActivity

@Module
abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}