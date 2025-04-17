package di

import logic.usecase.EasyFoodSuggestionGameUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single { EasyFoodSuggestionGameUseCase(get()) }
}