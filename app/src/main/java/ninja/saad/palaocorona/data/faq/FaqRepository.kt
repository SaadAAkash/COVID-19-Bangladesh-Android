package ninja.saad.palaocorona.data.faq

import io.reactivex.Single

interface FaqRepository {
    
    fun getFaq(): Single<MutableList<Faq>>
}