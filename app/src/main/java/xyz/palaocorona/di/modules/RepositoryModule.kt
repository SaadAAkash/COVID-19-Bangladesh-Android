package xyz.palaocorona.di.modules

import dagger.Binds
import dagger.Module
import xyz.palaocorona.data.authentication.AuthenticationRepository
import xyz.palaocorona.data.authentication.AuthenticationRepositoryImpl
import xyz.palaocorona.data.dashboard.DashboardRepository
import xyz.palaocorona.data.dashboard.DashboardRepositoryImpl
import xyz.palaocorona.data.liveupdates.LiveUpdateRepository
import xyz.palaocorona.data.liveupdates.LiveUpdateRepositoryImpl
import xyz.palaocorona.data.main.MainRepository
import xyz.palaocorona.data.main.MainRepositoryImpl
import xyz.palaocorona.data.news.NewsRepository
import xyz.palaocorona.data.news.NewsRepositoryImpl
import xyz.palaocorona.data.testyourself.TestYourselfRepository
import xyz.palaocorona.data.testyourself.TestYourselfRepositoryImpl

@Module
abstract class RepositoryModule {
    
    @Binds
    abstract fun provideMainRepository(repo: MainRepositoryImpl): MainRepository
    
    @Binds
    abstract fun provideAuthenticationRepository(repo: AuthenticationRepositoryImpl): AuthenticationRepository
    
    @Binds
    abstract fun provideTestYourselfRepository(repo: TestYourselfRepositoryImpl): TestYourselfRepository
    
    @Binds
    abstract fun provideDashboardRepository(repo: DashboardRepositoryImpl): DashboardRepository
    
    @Binds
    abstract fun provideNewsRepository(repo: NewsRepositoryImpl): NewsRepository
    
    @Binds
    abstract fun provideLiveUpdateRepository(repo: LiveUpdateRepositoryImpl): LiveUpdateRepository
}