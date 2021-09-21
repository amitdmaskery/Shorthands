package `in`.maskery.shorthands

class LinkedChannel<T,U>(val block: (T)-> U): MutableChannel<T, U>{

    private val terminalInterceptor = Interceptor { input: T, chain: Chain<T, U> -> block(input) }
    private var chain = InterceptorWrapper<T, U>(terminalInterceptor)

    override fun invoke(input: T): U {
        return synchronisedOverMe { chain.intercept(input) }
    }

    override fun addInterceptor(interceptor: Interceptor<T, U>){
        synchronisedOverMe {
            val nextInterceptorWrapper = InterceptorWrapper<T, U>(interceptor, chain)
            chain = nextInterceptorWrapper
        }
    }

    override fun removeInterceptor(interceptor: Interceptor<T, U>){
        synchronisedOverMe {
            if (interceptor!= terminalInterceptor){
                if (chain.interceptor == interceptor){
                    chain = chain.collection!!
                    return@synchronisedOverMe
                }
                var prevNode = chain
                var curNode = chain.collection
                while (curNode!=null && curNode.interceptor!= interceptor){
                    prevNode = curNode
                    curNode = curNode.collection
                }
                if (curNode!=null){
                    prevNode.collection = curNode.collection
                }
            }
        }
    }

    private fun <R> synchronisedOverMe(block: ()-> R): R{
        return synchronized(this){ block() }
    }
}