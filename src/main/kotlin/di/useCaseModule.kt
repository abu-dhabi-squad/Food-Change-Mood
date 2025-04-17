package di

import logic.GetItalianMealsForLargeGroupUsesCase
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI

val useCaseModule = module{
    single { FoodChangeMoodConsoleUI(get()) }
    single { GetItalianMealsForLargeGroupUsesCase(get()) }
}