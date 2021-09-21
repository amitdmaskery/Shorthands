package `in`.maskery.shorthands

import java.util.*

class ImmutableChannel<T, U> private constructor(private val block: (T)-> U, private val chain: InterceptorWrapper<T, U>): Channel<T, U> {
    override fun invoke(input: T): U { return chain.intercept(input) }

    class Builder<T, U>(private val block: (T)-> U): List<Interceptor<T, U>> by LinkedList<Interceptor<T, U>>(){
        fun build(): ImmutableChannel<T, U>{
            var chain = InterceptorWrapper<T, U>(Interceptor{ input: T, chain: Chain<T, U> -> block(input) })
            for (i in lastIndex downTo 0){
                chain = InterceptorWrapper<T, U>(get(i),chain)
            }
            return ImmutableChannel(block, chain)
        }
    }
}