package core.model

import core.model.abstracts.IObservable

/**
 * XMLAtribute
 *
 * Represents an atribute in xml.
 * Can be found inside XMLEntities: <tag atributeKey=atributeValue /tag>
 *
 *
 * @constructor
 *
 * @param key
 * @param value
 */
class XMLAtribute(
    key: String,
    value: String,
) : IObservable<(XMLAtribute) -> Unit> {

    constructor(name: String, value: Any) : this(name, value.toString())

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
    public var value: String = value
        private set(value) {
            field = value
            notifyObservers { it(this) }
        }

    /**
     * Change the value stored in the atribute
     *
     * @param newValue
     */
    fun changeValue(newValue: String) {
        value = newValue
        notifyObservers { it(this) }
    }

    /***
     * Converts the given attribute to String with the following format: atributeKey=atributeValue
     */
    override fun toString(): String {
        return "$key=\"$value\""
    }

    override val observers: MutableList<(XMLAtribute) -> Unit> = mutableListOf()
}
