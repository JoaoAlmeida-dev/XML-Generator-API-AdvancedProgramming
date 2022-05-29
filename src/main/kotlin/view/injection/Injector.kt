package view.injection

import controller.services.FileReader
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation

object Injector {
    fun create(c: KClass<*>): Any {
        val propertiesMap: Map<String, String> = FileReader.readFileAsMap("di.properties")
        val createdInstance = c.createInstance()
        c.declaredMemberProperties.filter {
            it.hasAnnotation<Inject>()
        }.forEach {
            println(it)
            println("${c.simpleName}.${it.name}")
            println(Class.forName(propertiesMap["${c.simpleName}.${it.name}"]))
            val injectedProperty =
                (Class.forName(propertiesMap["${c.simpleName}.${it.name}"]).kotlin).createInstance()
            //(it as KMutableProperty<*>).setter.call(createdInstance, DefaultSetup())
            (it as KMutableProperty<*>).setter.call(createdInstance, injectedProperty)
        }

        c.declaredMemberProperties.filter {
            it.hasAnnotation<InjectAdd>()
        }.forEach { it: KProperty1<out Any, *> ->
            println(it)
            val prop = it.call(createdInstance) as MutableCollection<Any>

            val injectedPropertiesStrings: String? = propertiesMap["${c.simpleName}.${it.name}"]
            injectedPropertiesStrings?.split(",")?.forEach { it2: String ->
                println("Injector::InjectADD::$it2")
                prop.add((Class.forName(it2).kotlin).createInstance())
            }                    //(it as KMutableProperty<*>).setter.call(createdInstance, DefaultSetup())
        }


        return createdInstance
    }
}