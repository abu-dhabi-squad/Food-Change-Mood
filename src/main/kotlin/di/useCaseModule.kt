package di

import org.koin.dsl.module

val useCaseModule = module{
    val getRandomFoodUseCase = single { GetRandomFoodUseCase(get()) }
    val guessFoodPreparationTimeUseCase = single { GuessFoodPreparationTimeUseCase() }


    single { GetMealForThinPeopleUseCase(get()) }

}