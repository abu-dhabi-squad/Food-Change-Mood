package di

import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import logic.GetItalianMealsForLargeGroupUseCase
import logic.usecase.GetIraqiMealsUseCase
import logic.usecase.GetKetoDietMealsUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GetRandomKetoMealUseCase
import logic.usecase.GetRandomMealsByCountryUseCase
import logic.usecase.GetRandomPotatoesMealsUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import logic.usecase.GetHealthyMealsUseCase
import org.koin.dsl.module
import presentation.GetFoodByDateUI
import presentation.GetMealByIdUI
import util.DateParserImplementation
import util.DateParserInterface
import util.GetFoodByDateValidationImplementaion
import util.GetFoodByDateValidationInterface

val useCaseModule = module {
    single { GetRandomFoodUseCase(get()) }
    single { GuessFoodPreparationTimeUseCase() }
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetHealthyMealsUseCase(get()) }
    single { GetRandomMealsByCountryUseCase(get()) }
    single { GetRandomPotatoesMealsUseCase(get()) }
    single { GetItalianMealsForLargeGroupUseCase(get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { GetKetoDietMealsUseCase(get()) }
    single { GetRandomKetoMealUseCase() }

    single { GetFoodByDateUseCase(get()) }
    single<DateParserInterface> { DateParserImplementation() }
    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion(get()) }
    single { GetMealByIdUI(get()) }
    single { GetMealByIdUseCase(get()) }
    single { GetFoodByDateUI(get(),get(), get(), get()) }
}