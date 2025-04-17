package di

import logic.usecase.GetIraqiMealsUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetIraqiMealsUseCase(get()) }
}

