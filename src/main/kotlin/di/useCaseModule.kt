package di

import logic.usecase.GymHelperUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GymHelperUseCase(get()) }
    single { GetMealForThinPeopleUseCase(get()) }
}