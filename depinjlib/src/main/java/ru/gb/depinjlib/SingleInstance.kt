package ru.gb.depinjlib

import kotlin.reflect.KClass

class SingleInstance<T>(
    codeDefinition: () -> T,
    override val qualifier: String? = null,
    override val primaryType: KClass<*>
) : InstanceFactory<T>(codeDefinition) {

    private var value: T? = null

    private fun getValue(): T = value ?: throw IllegalStateException("don't set value")

    override fun get(): T = if (isCreated()) getValue() else create()

    override fun isCreated(): Boolean = (value != null)

    override fun create(): T = if (isCreated()) getValue() else super.create()

}