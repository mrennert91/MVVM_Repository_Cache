package com.example.mvvmrepository.cache

import com.dropbox.android.external.store4.*
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

class CacheStore<Key : Any, Output : Any> {

    @OptIn(ExperimentalTime::class)
    fun getStore(listener: Listener<Output>, duration: Int): Store<Key, Output> {
        return StoreBuilder.from<Key, Output>(
                nonFlowValueFetcher {
                    listener.onExecute()
                }
        ).cachePolicy(
                MemoryPolicy.builder()
                        .setExpireAfterWrite(duration.minutes)
                        .build()
        ).build()
    }


    interface Listener<Output : Any> {
        suspend fun onExecute() : Output
    }
}