package ninja.saad.palaocorona.base.data.network

import android.content.Context
import com.google.gson.annotations.SerializedName
import ninja.saad.palaocorona.util.ConnectivityManager
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class RetrofitException(private val _message: String?,
                        private val _url: String?,
                        private val _response: Response<*>?,
                        private val _kind: Kind,
                        private val _exception: Throwable?,
                        private val _retrofit: Retrofit?
                        ) : RuntimeException(_message, _exception) {

    class RequestException(var httpCode: Int = 500, override var message: String = "") : Exception(message)
    class ApiException(val code: Int, val errorBody: ResponseBody?, override val message: String) : Exception(message)
    data class ApiError(
        @SerializedName("code") val statusCode: Int = 200,
        @SerializedName("message") val message: String = ""
    )

    companion object {
        fun httpError(url: String, response: Response<*>?, retrofit: Retrofit): RetrofitException {
            val message = response?.code().toString() + " " + response?.message()
            return RetrofitException(
                message,
                url,
                response,
                Kind.HTTP,
                null,
                retrofit
            )
        }

        fun httpErrorWithObject(url: String, response: Response<*>?, retrofit: Retrofit): RetrofitException {
            val message = response?.code().toString() + " " + response?.message()
            val error = RetrofitException(
                message,
                url,
                response,
                Kind.HTTP_422_WITH_DATA,
                null,
                retrofit
            )
            return error
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(
                exception.message,
                null,
                null,
                Kind.NETWORK,
                exception,
                null
            )
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(
                exception.message,
                null,
                null,
                Kind.UNEXPECTED,
                exception,
                null
            )
        }

        fun parseIOException(appContext: Context, throwable: Throwable, url: String): RetrofitException {
            return if (!ConnectivityManager.isConnectedToInternet(appContext, url)) {
                RetrofitException(
                    "Slow or no internet connection",
                    null,
                    null,
                    Kind.NETWORK,
                    throwable,
                    null
                )
            } else {
                RetrofitException(
                    "Unknown error occured",
                    null,
                    null,
                    Kind.UNEXPECTED,
                    throwable,
                    null
                )
            }
        }
        /**
         * parse [RequestException] from [okhttp3.OkHttpClient] response
         */
        fun parseRequestException(appContext: Context, code: Int, errorBody: ResponseBody? = null, message: String? = null): RequestException {
            errorBody?.let { body ->
                // parse error model from response
                val requestError: ApiError =
                    getConverter(
                        appContext,
                        ApiError::class.java,
                        body
                    )

                // if error response does not contain any specific message use a generic error message from resource
                return RequestException()
                    .apply {
                    this.message = if (requestError.message.isBlank()) {
                        "Failed, please try again later"
                    } else {
                        requestError.message
                    }

                    httpCode = code
                }
            }

            message?.let {msg ->
                return RequestException(
                    message = msg
                )
            }

            return RequestException(
                message = "Failed, please try again later"
            )
        }

        /**
         * Convert error response to a data class
         */
        private fun <T> getConverter(appContext: Context, clazz: Class<T>, errorBody: ResponseBody): T {
            return try {
                NetworkFactory.getRetrofit(appContext)
                    .responseBodyConverter<T>(clazz, arrayOfNulls<Annotation>(0))
                    .convert(errorBody)?.let { it } ?: throw Exception()
            } catch (ex: Exception) {
                clazz.newInstance()
            }
        }

    }

    /** The request URL which produced the error. */
    fun getUrl() = _url

    /** Response object containing status code, headers, body, etc. */
    fun getResponse() = _response

    /** The event kind which triggered this error. */
    fun getKind() = _kind

    /** The Retrofit this request was executed on */
    fun getRetrofit() = _retrofit

    /**
     * HTTP response body converted to specified `type`. `null` if there is no
     * response.
     * @throws IOException if unable to convert the body to the specified `type`.
     */
    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (_response == null || _response.errorBody() == null || _retrofit == null) {
            return null
        }
        val converter : Converter<ResponseBody, T> =
                _retrofit.responseBodyConverter(type, arrayOfNulls<Annotation>(0))
        return converter.convert(_response.errorBody())
    }


    enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,
        /** A non-200 HTTP status code was received from the server.  */
        HTTP,
        HTTP_422_WITH_DATA,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}