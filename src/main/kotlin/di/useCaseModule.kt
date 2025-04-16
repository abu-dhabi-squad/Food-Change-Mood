package di

import logic.usecase.GetRandomPotatoesMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRandomPotatoesMealsUseCase(get()) }
}