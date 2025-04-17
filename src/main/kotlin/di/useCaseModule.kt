package di

import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetFoodByDateUseCase(get()) }
}