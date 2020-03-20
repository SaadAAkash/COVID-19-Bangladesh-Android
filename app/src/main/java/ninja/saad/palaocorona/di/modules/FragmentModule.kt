package ninja.saad.palaocorona.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ninja.saad.palaocorona.di.annotations.PerFragment
import ninja.saad.palaocorona.ui.features.authentication.CreateProfileFragment
import ninja.saad.palaocorona.ui.features.authentication.otpverification.OtpVerificationFragment
import ninja.saad.palaocorona.ui.features.authentication.login.LoginFragment
import ninja.saad.palaocorona.ui.features.dashboard.DashboardFragment
import ninja.saad.palaocorona.ui.features.faq.FaqFragment
import ninja.saad.palaocorona.ui.features.news.NewsFragment

@Module
abstract class FragmentModule {
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindDashboardFragment(): DashboardFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindFaqFragment(): FaqFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindNewsFragment(): NewsFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): LoginFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindOtpVerificationFragment(): OtpVerificationFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindCreateProfileFragment(): CreateProfileFragment
    
    
}