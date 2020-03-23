package ninja.saad.palaocorona.data.authentication

import androidx.arch.core.executor.TaskExecutor
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.orhanobut.logger.Logger
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.data.authentication.model.User
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor() {
    
    companion object {
        const val USERS = "users"
    }
    
    fun isUserLoggedIn(): Boolean {
        return !FirebaseAuth.getInstance().uid.isNullOrEmpty()
    }
    
    fun sendOtp(phoneNumber: String): Single<String> {
        FirebaseAuth.getInstance().signOut()
        return Single.create<String>(object: SingleOnSubscribe<String> {
            override fun subscribe(emitter: SingleEmitter<String>) {
                val executor = Executors.newSingleThreadExecutor()
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS, executor,
                    object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                            executor.shutdown()
                        }
            
                        override fun onVerificationFailed(e: FirebaseException) {
                            e.printStackTrace()
                            if(!emitter.isDisposed) {
                                emitter.onError(e)
                            }
                            executor.shutdown()
                        }
            
                        override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                            
                            executor.shutdown()
                            emitter.onSuccess(verificationId)
                        }
            
                        override fun onCodeAutoRetrievalTimeOut(p0: String) {
                            executor.shutdown()
                            emitter.onError(TimeoutException())
                        }
                    })
            }
    
        })
    }
    
    fun verifyOtp(verificationId: String, otp: String): Single<String> {
        return Single.create { emitter ->
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        emitter.onSuccess(it.result!!.user!!.uid)
                    } else {
                        if(it.exception != null && it.exception is FirebaseNetworkException) {
                            emitter.onError(it.exception!!)
                        } else {
                            emitter.onError(Throwable("Cannot verify OTP"))
                        }
                    }
                }.addOnFailureListener {
                    if(!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
    }
    
    fun getUserInfo(userId: String): Maybe<User> {
        return Maybe.create<User> { emitter ->
            val db = FirebaseDatabase.getInstance()
                .getReference(USERS)
                .child(userId)
            db.keepSynced(true)
            
            db.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        if(!emitter.isDisposed) {
                            emitter.onError(error.toException())
                        }
                    }
    
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(dataSnapshot.exists()) {
                            try {
                                emitter.onSuccess(dataSnapshot.getValue(User::class.java)!!)
                            } catch (e: Exception) {
                                emitter.onError(e)
                            }
                        } else {
                            emitter.onError(Throwable("User not exists"))
                        }
                    }
    
                })
        }
    }
    
    fun saveProfile(user: User): Completable {
        val uid = FirebaseAuth.getInstance().uid
        return Completable.create { emitter ->
            if(uid != null) {
                val db = FirebaseDatabase.getInstance()
                    .getReference(USERS)
                    .child(uid)
                db.keepSynced(true)
                
                db.setValue(user)
                    .addOnSuccessListener {
                        emitter.onComplete()
                    }.addOnFailureListener {
                        emitter.onError(it)
                    }
            } else {
                emitter.onError(Throwable("User not registered"))
            }
        }
    }
}