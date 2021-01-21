package com.example.saitowapp.network

import okhttp3.Interceptor


class HttpHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val authrequest = request.newBuilder()
            .addHeader("Accept", "application/vnd.saitowag.api+json;version=1.0").build()
        return chain.proceed(authrequest)
    }
}