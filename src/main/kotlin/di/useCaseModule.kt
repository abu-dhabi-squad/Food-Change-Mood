package di

import logic.usecase.GetHealthyMealsUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single { GetHealthyMealsUseCase(get()) }

}