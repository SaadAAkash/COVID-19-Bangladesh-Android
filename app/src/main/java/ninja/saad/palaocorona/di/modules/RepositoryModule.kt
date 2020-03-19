package ninja.saad.palaocorona.di.modules

import dagger.Binds
import dagger.Module
import ninja.saad.palaocorona.data.faq.FaqRepository
import ninja.saad.palaocorona.data.faq.FaqRepositoryImpl

@Module
abstract class RepositoryModule {
    
    @Binds
    abstract fun provideFaqRepository(repo: FaqRepositoryImpl): FaqRepository
}