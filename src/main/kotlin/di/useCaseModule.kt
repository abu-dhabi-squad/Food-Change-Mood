package di

import logic.usecase.GetRandomMealsByCountryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRandomMealsByCountryUseCase(get()) }
}