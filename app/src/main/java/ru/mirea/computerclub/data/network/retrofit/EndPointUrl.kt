package ru.mirea.computerclub.data.network.retrofit

@Target(allowedTargets = [AnnotationTarget.CLASS])
@Retention(value = AnnotationRetention.RUNTIME)
internal annotation class EndpointUrl(val value: String)