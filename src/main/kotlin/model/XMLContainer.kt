package model

interface XMLContainer {

    fun removeChild(entity: Entity)
    fun addChild(entity: Entity)
    fun getDepth(): Int
}