package ninja.saad.palaocorona.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ninja.saad.palaocorona.di.annotations.PerFragment
import ninja.saad.palaocorona.ui.features.about.AboutCovidFragment
import ninja.saad.palaocorona.ui.features.authentication.createprofile.CreateProfileFragment
import ninja.saad.palaocorona.ui.features.authentication.otpverification.OtpVerificationFragment
import ninja.saad.palaocorona.ui.features.authentication.login.LoginFragment
import ninja.saad.palaocorona.ui.features.dashboard.DashboardFragment
import ninja.saad.palaocorona.ui.features.faq.FaqFragment
import ninja.saad.palaocorona.ui.features.news.NewsFragment
import ninja.saad.palaocorona.ui.features.quarantine.QuarantineFragment
import ninja.saad.palaocorona.ui.features.testyourself.TestYourselfFragment

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
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindAboutCovidFragment(): AboutCovidFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindQuarantineFragment(): QuarantineFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindTestYourselfFragment(): TestYourselfFragment
    
    
}