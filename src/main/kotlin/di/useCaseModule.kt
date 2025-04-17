package di

import logic.usecase.EasyFoodSuggestionGameUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single { GetMealForThinPeopleUseCase(get()) }
    single { EasyFoodSuggestionGameUseCase(get()) }
}

