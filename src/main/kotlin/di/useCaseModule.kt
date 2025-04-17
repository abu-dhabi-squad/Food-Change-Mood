package di

import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetRandomPotatoesMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetRandomPotatoesMealsUseCase(get()) }
}