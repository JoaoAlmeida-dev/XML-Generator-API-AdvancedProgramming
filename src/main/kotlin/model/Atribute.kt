package core.model

data class Atribute(
    var key: String,
    var value: String,
) {

    constructor(name: String, value: Any) : this(name, value.toString())

    override fun toString(): String {
        return "$key=\"$value\""
    }
}
