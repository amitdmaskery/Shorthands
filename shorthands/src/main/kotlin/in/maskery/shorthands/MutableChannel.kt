package `in`.maskery.shorthands

interface MutableChannel<T, U>: Channel<T, U> {
    fun addInterceptor(interceptor: Interceptor<T, U>)
    fun removeInterceptor(interceptor: Interceptor<T, U>)
}

fun <T, U> mutableChannelFor(block: (T) -> U): MutableChannel<T, U>{ return LinkedChannel<T, U>(block) }