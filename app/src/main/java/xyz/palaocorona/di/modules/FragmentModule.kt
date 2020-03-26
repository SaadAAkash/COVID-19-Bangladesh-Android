package xyz.palaocorona.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import xyz.palaocorona.di.annotations.PerFragment
import xyz.palaocorona.ui.features.about.AboutCovidFragment
import xyz.palaocorona.ui.features.authentication.createprofile.CreateProfileFragment
import xyz.palaocorona.ui.features.authentication.otpverification.OtpVerificationFragment
import xyz.palaocorona.ui.features.authentication.login.LoginFragment
import xyz.palaocorona.ui.features.authentication.profile.ProfileFragment
import xyz.palaocorona.ui.features.contact.ContactFragment
import xyz.palaocorona.ui.features.dashboard.DashboardFragment
import xyz.palaocorona.ui.features.dosanddonts.DosAndDontsFragment
import xyz.palaocorona.ui.features.faq.FaqFragment
import xyz.palaocorona.ui.features.liveupdates.LiveUpdatesFragment
import xyz.palaocorona.ui.features.news.NewsFragment
import xyz.palaocorona.ui.features.quarantine.QuarantineFragment
import xyz.palaocorona.ui.features.testyourself.TestYourSelfResultFragment
import xyz.palaocorona.ui.features.testyourself.TestYourselfFragment

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
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindLiveUpdatesFragment(): LiveUpdatesFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindProfileFragment(): ProfileFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindTestYourSelfResultFragment(): TestYourSelfResultFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindContactFragment(): ContactFragment
    
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindDosAndDontsFragment(): DosAndDontsFragment
    
}