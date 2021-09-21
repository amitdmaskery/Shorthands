package `in`.maskery.shorthands

interface Channel<T, U> {
    operator fun invoke(input: T): U
}
