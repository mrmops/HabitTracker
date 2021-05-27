package com.example.domain.Infrastructure

fun <K, V> Map<K, V>.firstOrNull(filter: (Pair<K,V>) -> Boolean): Pair<K,V>? {
    for (item in this){
        val pair = item.toPair()
        if(filter(pair)){
            return pair
        }
    }
    return null
}
