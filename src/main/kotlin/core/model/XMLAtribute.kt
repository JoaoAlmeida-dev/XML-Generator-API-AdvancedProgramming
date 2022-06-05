package core.model

import view.IObservable

class XMLAtribute(
    key: String,
    value: String,
) : IObservable<(XMLAtribute) -> Unit> {
    public var key: String = key
        private set(value) {
            field = value
            notifyObservers { it(this) }
        }
    public var value: String = value
        private set(value) {
            field = value
            notifyObservers { it(this) }
        }

    fun rename(text: String) {
        value = text
        notifyObservers { it(this) }
    }

    constructor(name: String, value: Any) : this(name, value.toString())

    override fun toString(): String {
        return "$key=\"$value\""
    }

    override val observers: MutableList<(XMLAtribute) -> Unit> = mutableListOf()
}
