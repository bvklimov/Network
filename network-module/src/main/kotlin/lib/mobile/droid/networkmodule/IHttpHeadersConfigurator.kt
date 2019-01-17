package lib.mobile.droid.networkmodule

import okhttp3.Request

abstract class IHttpHeadersConfigurator {
    fun configureRequest(request: Request): Request {
        if (headers == null || headers!!.isEmpty()) return request
        val builder = request.newBuilder()
        for((headerName, headerValue) in headers!!) {
            builder.addHeader(headerName, headerValue)
        }
        return builder.build()
    }

    abstract val headers: Map<String, String>?
}