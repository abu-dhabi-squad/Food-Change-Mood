package di

import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetRandomPotatoesMealsUseCase
import logic.usecase.GetRandomMealsByCountryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetRandomMealsByCountryUseCase(get()) }
    single { GetRandomPotatoesMealsUseCase(get()) }
}