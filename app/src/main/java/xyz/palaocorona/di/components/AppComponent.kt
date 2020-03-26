package xyz.palaocorona.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import xyz.palaocorona.base.BaseApplication
import xyz.palaocorona.di.modules.*
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */

@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        FragmentModule::class,
        RepositoryModule::class
    ]
)

@Singleton
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}