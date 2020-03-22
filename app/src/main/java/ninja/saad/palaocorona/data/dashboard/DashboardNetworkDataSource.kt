package ninja.saad.palaocorona.data.dashboard

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Single
import javax.inject.Inject

class DashboardNetworkDataSource @Inject constructor() {
    
    companion object {
        const val DIR_SLIDER_IMAGES = "gujob_na_ki"
    }
    
    fun isUserLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().uid != null
    }
    
    fun getSliderImages(): Single<MutableList<String>> {
        return Single.create { emitter ->
            val urls = mutableListOf<String>()
            val storage = FirebaseStorage.getInstance()
            storage.reference
                .child(DIR_SLIDER_IMAGES)
                .listAll()
                .addOnSuccessListener { result ->
                    result.items.forEach { reference ->
                        reference.downloadUrl.addOnSuccessListener {
                            urls.add(it.toString())
                            if(urls.size == result.items.size) {
                                emitter.onSuccess(urls)
                            }
                        }.addOnFailureListener {
                            if(!emitter.isDisposed){
                                emitter.onError(it)
                            }
                            it.printStackTrace()
                        }
                    }
                }.addOnFailureListener {
                    if(!emitter.isDisposed){
                        emitter.onError(it)
                    }
                }
        }
    }
}