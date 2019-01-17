package lib.mobile.droid.networkmodule

import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

fun makeRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient
): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())//todo move to param
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()


fun makeOkHttpBuilder(
    okHttpTimeout: Long,
    connectionStateChecker: IConnectionStateChecker?,
    httpHeadersConfigurator: IHttpHeadersConfigurator?
): OkHttpClient.Builder {
    return OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logger)
            addNetworkInterceptor(StethoInterceptor())
        }
        connectTimeout(okHttpTimeout, TimeUnit.SECONDS)
        readTimeout(okHttpTimeout, TimeUnit.SECONDS)
        writeTimeout(okHttpTimeout, TimeUnit.SECONDS)
        addInterceptor { chain ->
            connectionStateChecker?.checkForConnectionState()
            httpHeadersConfigurator?.configureRequest(chain.request())
            chain.proceed(chain.request())
        }
    }
}