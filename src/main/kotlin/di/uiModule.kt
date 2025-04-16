package di

import org.koin.dsl.module
import presentation.GymHelperConsoleUI

val uiModule = module {
    single { GymHelperConsoleUI(get()) }
}