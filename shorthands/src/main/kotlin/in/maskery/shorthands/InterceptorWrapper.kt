package `in`.maskery.shorthands

internal class InterceptorWrapper<T, U> constructor(
    val interceptor: Interceptor<T, U>,
    var collection: InterceptorWrapper<T, U>? = null
) : Chain<T, U> {

    fun intercept(input: T): U {
        return interceptor.intercept(input, this)
    }

    override fun proceed(input: T): U {
        return collection?.intercept(input) ?: throw NoInterceptorsDownTheLine()
    }
}