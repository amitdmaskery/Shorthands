package `in`.maskery.shorthands

fun interface Interceptor<T, U>{
    fun intercept(input: T, chain: Chain<T, U>): U
}