package di

import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import org.koin.dsl.module

val useCaseModule = module {
     single { GetRandomFoodUseCase(get()) }
    single { GuessFoodPreparationTimeUseCase() }


    single { GetMealForThinPeopleUseCase(get()) }

}