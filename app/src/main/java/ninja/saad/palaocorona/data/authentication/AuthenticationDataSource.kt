package ninja.saad.palaocorona.data.authentication

import androidx.arch.core.executor.TaskExecutor
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor() {
    
    fun sendOtp(phoneNumber: String): Single<String> {
        return Single.create<String>(object: SingleOnSubscribe<String> {
            override fun subscribe(emitter: SingleEmitter<String>) {
                val executor = Executors.newSingleThreadExecutor()
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS, executor,
                    object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                            Logger.d("Completed")
                            executor.shutdown()
                        }
            
                        override fun onVerificationFailed(e: FirebaseException) {
                            e.printStackTrace()
                            executor.shutdown()
                        }
            
                        override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                            Logger.d("Code Sent $verificationId")
                            executor.shutdown()
                            emitter.onSuccess(verificationId)
                        }
            
                        override fun onCodeAutoRetrievalTimeOut(p0: String) {
                            Logger.d("Timeout")
                            executor.shutdown()
                            emitter.onError(TimeoutException())
                        }
                    })
            }
    
        })
    }
    
    fun verifyOtp(verificationId: String, otp: String): Single<Boolean> {
        return Single.create { emitter ->
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        emitter.onSuccess(true)
                    } else {
                        emitter.onSuccess(false)
                    }
                }.addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }
}