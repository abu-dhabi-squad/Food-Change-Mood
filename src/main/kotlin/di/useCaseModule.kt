package di

import logic.usecase.EasyFoodSuggestionGameUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import org.koin.dsl.module
import presentation.GetEasyFoodSuggestionUI

val useCaseModule = module{
    single { GetMealForThinPeopleUseCase(get()) }
    single { EasyFoodSuggestionGameUseCase(get()) }
    single { GetEasyFoodSuggestionUI(get()) }
}

