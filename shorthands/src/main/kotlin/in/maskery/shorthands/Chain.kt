package `in`.maskery.shorthands

fun interface Chain<T, U>{
    fun proceed(input: T): U
}