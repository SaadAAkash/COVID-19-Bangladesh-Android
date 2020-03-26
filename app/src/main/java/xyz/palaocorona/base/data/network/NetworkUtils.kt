package xyz.palaocorona.base.data.network

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import java.lang.Exception

fun <T>Single<Response<T>>.onResponse(): Single<T> {
    return map {
        if(it.isSuccessful) {
            if(it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}

fun <T>Observable<Response<T>>.onResponse(): Observable<T> {
    return map {
        if(it.isSuccessful) {
            if(it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}

fun <T>Flowable<Response<T>>.onResponse(): Flowable<T> {
    return map {
        if(it.isSuccessful) {
            if(it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}

fun <T>Maybe<Response<T>>.onResponse(): Maybe<T> {
    return map {
        if(it.isSuccessful) {
            if(it.body() != null) {
                it.body()
            } else {
                throw Exception("Request Exception")
            }
        } else {
            throw Exception(it.message())
        }
    }
}