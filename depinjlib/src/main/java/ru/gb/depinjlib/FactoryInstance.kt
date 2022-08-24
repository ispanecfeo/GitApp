package ru.gb.depinjlib

import kotlin.reflect.KClass

class FactoryInstance<T>(
    codeDefinition: () -> T,
    override val qualifier: String? = null,
    override val primaryType: KClass<*>
) : InstanceFactory<T>(codeDefinition) {

    override fun get(): T = super.create()

    override fun isCreated(): Boolean = false

}