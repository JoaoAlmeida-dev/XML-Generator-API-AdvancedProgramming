package newArquitecture

class Atribute(
    val value: Any,
    val name: String
) {
    override fun toString(): String {
        return "$name=\"$value\""
    }
}
