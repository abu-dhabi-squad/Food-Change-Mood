package di

import logic.usecase.GymHelperUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GymHelperUseCase(get()) }
}