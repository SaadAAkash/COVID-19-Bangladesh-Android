package ninja.saad.palaocorona.data.faq

import android.content.Context
import io.reactivex.Single
import ninja.saad.palaocorona.base.data.local.AssetsDataSource
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(private val context: Context,
                                            private val assetsDataSource: AssetsDataSource): FaqRepository {
    
    override fun getFaq(): Single<MutableList<Faq>> {
        return assetsDataSource.getFaq(context)
    }
}