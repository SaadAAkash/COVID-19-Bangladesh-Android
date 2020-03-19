package ninja.saad.palaocorona.base.data.network

import android.content.Context
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import ninja.saad.palaocorona.util.ConnectivityManager
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory(private val context: Context): CallAdapter.Factory() {

    private val _original by lazy {
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    companion object {
        fun create(context: Context) : CallAdapter.Factory =
            RxErrorHandlingCallAdapterFactory(
                context
            )
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        val wrapped = _original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>

        val rawType = getRawType(returnType)

        val isFlowable = rawType == Flowable::class.java
        val isSingle = rawType == Single::class.java
        val isMaybe = rawType == Maybe::class.java
        val isCompletable = rawType == Completable::class.java

        return if(isFlowable) RxFlowableCallAdapterWrapper(context, retrofit, wrapped)
        else if(isSingle) RxSingleCallAdapterWrapper(context, retrofit, wrapped)
        else if(isMaybe) RxMaybeCallAdapterWrapper(context, retrofit, wrapped)
        else if(isCompletable) RxCompletableCallAdapterWrapper(context, retrofit, wrapped)
        else RxObservableCallAdapterWrapper(context, retrofit, wrapped)
    }

    private inner class RxSingleCallAdapterWrapper<R>(val context: Context,
                                          val retrofit: Retrofit,
                                          val wrappedCallAdapter: CallAdapter<R, *>
                                          ): CallAdapter<R, Single<R>> {

        override fun responseType(): Type = wrappedCallAdapter.responseType()

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Single<R> {
            val adapted = (wrappedCallAdapter.adapt(call) as Single<R>)
            return adapted.onErrorResumeNext { throwable: Throwable ->
                Single.error(asRetrofitException(context, throwable, call.request().url().host(), retrofit))
            }
        }
    }

    private inner class RxFlowableCallAdapterWrapper<R>(val context: Context,
                                                      val retrofit: Retrofit,
                                                      val wrappedCallAdapter: CallAdapter<R, *>
    ): CallAdapter<R, Flowable<R>> {

        override fun responseType(): Type = wrappedCallAdapter.responseType()

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Flowable<R> {
            val adapted = (wrappedCallAdapter.adapt(call) as Flowable<R>)
            return adapted.onErrorResumeNext { throwable: Throwable ->
                Flowable.error(asRetrofitException(context, throwable, call.request().url().host(), retrofit))
            }
        }
    }

    private inner class RxMaybeCallAdapterWrapper<R>(val context: Context,
                                                        val retrofit: Retrofit,
                                                        val wrappedCallAdapter: CallAdapter<R, *>
    ): CallAdapter<R, Maybe<R>> {

        override fun responseType(): Type = wrappedCallAdapter.responseType()

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Maybe<R> {
            val adapted = (wrappedCallAdapter.adapt(call) as Maybe<R>)
            return adapted.onErrorResumeNext { throwable: Throwable ->
                Maybe.error(asRetrofitException(context, throwable, call.request().url().host(), retrofit))
            }
        }
    }

    private inner class RxObservableCallAdapterWrapper<R>(val context: Context,
                                                        val retrofit: Retrofit,
                                                        val wrappedCallAdapter: CallAdapter<R, *>
    ): CallAdapter<R, Observable<R>> {

        override fun responseType(): Type = wrappedCallAdapter.responseType()

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Observable<R> {
            val adapted = (wrappedCallAdapter.adapt(call) as Observable<R>)
            return adapted.onErrorResumeNext { throwable: Throwable ->
                Observable.error(asRetrofitException(context, throwable, call.request().url().host(), retrofit))
            }
        }
    }

    private inner class RxCompletableCallAdapterWrapper<R>(val context: Context,
                                                        val retrofit: Retrofit,
                                                        val wrappedCallAdapter: CallAdapter<R, *>
    ): CallAdapter<R, Completable> {

        override fun responseType(): Type = wrappedCallAdapter.responseType()

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Completable {
            val adapted = (wrappedCallAdapter.adapt(call) as Completable)
            return adapted.onErrorResumeNext { throwable: Throwable ->
                Completable.error(asRetrofitException(context, throwable, call.request().url().host(), retrofit))
            }
        }
    }

    private fun asRetrofitException(context: Context, throwable: Throwable, host: String, retrofit: Retrofit): RetrofitException {
        // We had non-200 http error
        if (throwable is HttpException) {
            val response = throwable.response()

            if (throwable.code() == 422) {
                // on out api 422's get metadata in the response. Adjust logic here based on your needs
                return RetrofitException.httpErrorWithObject(response?.raw()?.request()?.url().toString(), response, retrofit)
            } else {
                return RetrofitException.httpError(response?.raw()?.request()?.url().toString(), response, retrofit)
            }
        }

        // A network error happened
        if(!ConnectivityManager.isConnectedToInternet(context, host)) {
            return RetrofitException.parseIOException(context, throwable, host)
        }
        else if (throwable is IOException) {
            return RetrofitException.parseIOException(context,throwable, host)
        }

        // We don't know what happened. We need to simply convert to an unknown error
        return RetrofitException.unexpectedError(throwable)
    }
}