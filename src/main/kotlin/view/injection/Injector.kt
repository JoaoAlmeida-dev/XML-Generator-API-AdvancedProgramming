package view.injection

import core.utilities.services.FileReader
import java.net.URL
import java.net.URLClassLoader
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation

object Injector {

    val propertiesMap: Map<String, String> = FileReader.readFileAsMap("di.properties")

    public final fun create(c: KClass<*>): Any {
        val createdInstance = c.createInstance()
        return inject(createdInstance)
    }

    public final fun inject(
        createdInstance: Any,
    ): Any {
        val dirUrl: URL = URL("file:/XML-Generator-API-AdvancedProgramming")
        val cl = URLClassLoader(mutableListOf(dirUrl).toTypedArray())

        val c = createdInstance::class
        c.declaredMemberProperties.filter {
            it.hasAnnotation<Inject>()
        }.forEach {
            val classNameInMap = propertiesMap["${c.simpleName}.${it.name}"]
            println("INJECTOR::ADD::${c.simpleName}.${it.name}")
            println("INJECTOR::ADD::" + Class.forName(propertiesMap["${c.simpleName}.${it.name}"]))
            val injectedProperty =
                (Class.forName(propertiesMap["${c.simpleName}.${it.name}"]).kotlin).createInstance()
            //(it as KMutableProperty<*>).setter.call(createdInstance, DefaultSetup())
            (it as KMutableProperty<*>).setter.call(createdInstance, injectedProperty)
        }

        c.declaredMemberProperties.filter {
            it.hasAnnotation<InjectAdd>()
        }.forEach { it: KProperty1<out Any, *> ->
            val prop = it.call(createdInstance) as MutableCollection<Any>
            val classFilePathFromFile: String? = propertiesMap["${c.simpleName}.${it.name}"]

            if (classFilePathFromFile != null) {
                println("INJECTOR::ADD::${c.simpleName}.${it.name}")
                println("INJECTOR::ADD::$classFilePathFromFile")

                classFilePathFromFile.split(",").forEach(fun(innerIt: String) {
                    val instance = cl.loadClass(innerIt).kotlin.createInstance()
                    prop.add(instance)
                })
                //(it as KMutableProperty<*>).setter.call(createdInstance, DefaultSetup())}
            }

        }
        return createdInstance

    }
}