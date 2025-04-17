package di

import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import org.koin.dsl.module
import presentation.GetFoodByDateUI
import util.GetFoodByDateValidationImplementaion
import util.GetFoodByDateValidationInterface

val useCaseModule = module{
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetFoodByDateUseCase(get()) }

    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion() }
    single { GetFoodByDateUI(get(),get()) }
}