package testbed

import view.WindowSkeleton
import view.custom.commands.atributepanel.RemoveAtributeCommandMenuItem
import view.injection.Injector


fun main() {
    println(RemoveAtributeCommandMenuItem()::class.java.name)
    val w = Injector.create(WindowSkeleton::class) as WindowSkeleton
    w.open()

}

