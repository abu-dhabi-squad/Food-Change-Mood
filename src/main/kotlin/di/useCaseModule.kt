package di

import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetHealthyMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetHealthyMealsUseCase(get())}

