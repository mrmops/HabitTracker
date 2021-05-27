package com.example.data.Models

abstract class Mapper<T, R> {
    
    fun mapList(objects: List<T>): List<R> = objects.map(this::map)

    fun reverseMapList(objects: List<R>): List<T> = objects.map(this::reverseMap)
    
    abstract fun map(obj: T): R

    abstract fun reverseMap(obj: R): T
}