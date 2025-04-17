package di

import logic.usecase.GetFoodByDateUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetFoodByDateUseCase(get()) }
}