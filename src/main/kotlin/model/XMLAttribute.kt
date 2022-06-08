package model

import model.abstracts.IObservable

/**
 * XMLAtribute
 *
 * Represents an atribute in xml.
 * Can be found inside XMLEntities: <tag atributeKey=atributeValue /tag>
 *
 * Stores a key value pair, where the key must be string the value can be Any
 *
 * @constructor
 *
 * @param key
 * @param value
 */
class XMLAttribute(
    key: String,
    value: Any,
) : IObservable<(XMLAttribute) -> Unit> {
    override val observers: MutableList<(XMLAttribute) -> Unit> = mutableListOf()

    /**
     * Key
     */
    public var key: String = key
        private set(value) {
            field = value
            notifyObservers { it(this) }
        }

    /**
     * Value
     */
    public var value: Any = value
        private set(value) {
            field = value
            notifyObservers { it(this) }
        }

    /**
     * Change the value stored in the atribute
     *
     * @param newValue
     */
    fun changeValue(newValue: Any) {
        value = newValue
        notifyObservers { it(this) }
    }

    /***
     * Converts the given attribute to String with the following format: atributeKey=atributeValue
     */
    override fun toString(): String {
        return "$key=\"$value\""
    }

}
