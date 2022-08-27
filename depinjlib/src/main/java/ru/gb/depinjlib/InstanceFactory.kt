package ru.gb.depinjlib

import kotlin.reflect.KClass

abstract class InstanceFactory<T>(private val codeDefinition: () -> T ) : Any() {

    abstract fun get() : T

    abstract val qualifier : String?

    abstract fun isCreated() : Boolean

    abstract val primaryType: KClass<*>

    open fun create() : T {
        return codeDefinition.invoke()
    }



}