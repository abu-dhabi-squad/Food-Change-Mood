package di

import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import org.koin.dsl.module

val useCaseModule = module{
    val getRandomFoodUseCase = single { GetRandomFoodUseCase(get()) }
    val guessFoodPreparationTimeUseCase = single { GuessFoodPreparationTimeUseCase() }
}