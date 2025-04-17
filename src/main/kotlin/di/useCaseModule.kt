package di

import logic.usecase.MealSearchByNameUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single { MealSearchByNameUseCase(get()) }
}