package view

import core.model.Entity
import java.awt.Font
import java.awt.Graphics
import javax.swing.*


/**
 * Interface for observable objects
 */
interface IObservable<O> {

    // Implementers have to provide this property
    val observers: MutableList<O>

    fun addObserver(observer: O) {
        observers.add(observer)
    }

    fun removeObserver(observer: O) {
        observers.remove(observer)
    }

    fun notifyObservers(handler: (O) -> Unit) {
        observers.toList().forEach { handler(it) }
    }
}





