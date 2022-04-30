package newArquitecture.coreModel

class Atribute(
    val name: String,
    val value: Any,
) {
    override fun toString(): String {
        return "$name=\"$value\""
    }
}
