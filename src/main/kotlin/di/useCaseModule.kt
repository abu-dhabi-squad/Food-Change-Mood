package di

import logic.GetItalianMealsForLargeGroupUsesCase
import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetRandomPotatoesMealsUseCase
import logic.usecase.GetRandomMealsByCountryUseCase
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI

val useCaseModule = module {
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetRandomMealsByCountryUseCase(get()) }
    single { GetRandomPotatoesMealsUseCase(get()) }
    single { GetItalianMealsForLargeGroupUsesCase(get()) }
}