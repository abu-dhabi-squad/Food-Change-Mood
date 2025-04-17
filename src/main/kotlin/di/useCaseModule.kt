package di

import logic.usecase.GetMealForThinPeopleUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMealForThinPeopleUseCase(get()) }
}

