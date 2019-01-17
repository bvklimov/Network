package lib.mobile.droid.networkmodule

open class RestServiceFactory<T : Any>(
    private val retrofitFactory: RetrofitFactory,
    private val clazz: Class<T>
) {
    lateinit var service: T

    init {
        makeRestService()
    }

    private fun makeRestService() {
        service = retrofitFactory.retrofit.create(clazz)
    }
}
