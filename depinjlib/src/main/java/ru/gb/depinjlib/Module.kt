package ru.gb.depinjlib

import android.content.Context
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

class Module{

    private val _mapping = hashMapOf<String, InstanceFactory<*>>()
    val mapping:Map<String, InstanceFactory<*>>
        get() = _mapping

    fun saveMapping(key: String, value: InstanceFactory<*>) {
        _mapping[key] = value
    }

    private var _androidContext : Context? = null

    val androidContext: Context
        get() = _androidContext!!

    inline fun<reified T>single(qualifier: String? = null, noinline codeDefinition: () -> T ) {
        val instance = SingleInstance<T>(codeDefinition, qualifier, T::class)
        val key = indexKey(instance)
        saveMapping(key, instance)
    }

    inline fun <reified T>factory(qualifier: String? = null, noinline codeDefinition: () -> T) {
        val instance = FactoryInstance<T>(codeDefinition, qualifier, T::class)
        val key = indexKey(instance)
        saveMapping(key, instance)
    }

    inline fun <reified T> get(qualifier: String? = null) : T {
        val key = indexKey(T::class, qualifier)
        if (!mapping.containsKey(key)) {
            throw IllegalArgumentException("no element in map")
        }
        return mapping[key]?.get() as T
    }

    fun indexKey(instance: InstanceFactory<*>): String {
        val tq = instance.qualifier ?: ""
        return "${instance.primaryType.java.name}:$tq"
    }

    fun indexKey(clazz: KClass<*>, qualifier: String? = null):String {
        val tq = qualifier ?: ""
        return "${clazz.java.name}:$tq"
    }

    fun setup(context: Context) {
        _androidContext = context
    }

}


fun module(moduleDeclaration: Module.() -> Unit) : Module {
    val module = Module()
    moduleDeclaration.invoke(module)
    return module
}



