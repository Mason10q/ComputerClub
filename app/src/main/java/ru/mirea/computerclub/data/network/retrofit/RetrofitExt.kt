package ru.mirea.computerclub.data.network.retrofit

import retrofit2.Retrofit

internal inline fun <reified T : Any> Retrofit.Builder.buildApi(): T =
    baseUrl(getServiceUrl(T::class.java))
        .build()
        .create(T::class.java)

internal fun getServiceUrl(serviceClass: Class<*>): String {
    val annotation = serviceClass.getAnnotation(EndpointUrl::class.java)
        ?: error("$serviceClass should have @EndpointUrl annotation")

    return annotation.value
}