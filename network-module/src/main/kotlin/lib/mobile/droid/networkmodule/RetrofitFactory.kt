package lib.mobile.droid.networkmodule

import okhttp3.OkHttpClient
import retrofit2.Retrofit

open class RetrofitFactory(
    private val apiConfig: IApiConfig,
    private val connectStateChecker: IConnectionStateChecker? = null,
    private val httpHeadersConfigurator: IHttpHeadersConfigurator? = null
) {
    val retrofit: Retrofit

    init {
        retrofit = initRetrofit()
    }

    private fun initRetrofit() =
        makeRetrofit(apiConfig.baseUrl, getOkHttpClient())

    private fun getOkHttpClient(): OkHttpClient {
        val builder = makeOkHttpBuilder(
            apiConfig.timeout,
            connectStateChecker,
            httpHeadersConfigurator
        )
        return createOkHttpBuilder(builder).build()
    }

    protected open fun createOkHttpBuilder(
        builder: OkHttpClient.Builder
    ) = builder
}



