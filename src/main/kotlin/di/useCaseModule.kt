package di

import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import org.koin.dsl.module
import presentation.GetFoodByDateUI
import presentation.GetMealByIdUI
import util.DateParserImplementation
import util.DateParserInterface
import util.GetFoodByDateValidationImplementaion
import util.GetFoodByDateValidationInterface

val useCaseModule = module{
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetFoodByDateUseCase(get()) }

    single<DateParserInterface> { DateParserImplementation() }
    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion(get()) }
    single { GetMealByIdUI(get()) }
    single { GetMealByIdUseCase(get()) }
    single { GetFoodByDateUI(get(),get(), get(), get()) }
}