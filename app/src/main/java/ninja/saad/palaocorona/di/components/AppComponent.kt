package ninja.saad.palaocorona.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ninja.saad.palaocorona.base.BaseApplication
import ninja.saad.palaocorona.di.modules.ActivityModule
import ninja.saad.palaocorona.di.modules.ApplicationModule
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */

@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class
    ]
)

@Singleton
interface AppComponent {

    fun inject(app: BaseApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}