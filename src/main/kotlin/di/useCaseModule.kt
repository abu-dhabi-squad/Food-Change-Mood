package di

import logic.usecase.GetRandomPotatoMealsUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single{GetRandomPotatoMealsUseCase(get())}
}