package di

import logic.GetItalianMealsForLargeGroupUseCase
import logic.usecase.*
import util.*
import org.koin.dsl.module
import presentation.GetFoodByDateUI
import presentation.GetMealByIdUI

val useCaseModule = module {
    single { GetRandomFoodUseCase(get()) }
    single { GuessFoodPreparationTimeUseCase() }
    single { GymHelperUseCase(get()) }
    single { GetMealForThinPeopleUseCase(get()) }
    single { GetHealthyMealsUseCase(get()) }
    single { GetRandomMealsByCountryUseCase(get()) }
    single { GetRandomPotatoesMealsUseCase(get()) }
    single { GetItalianMealsForLargeGroupUseCase(get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { GuessIngredientUseCase(get()) }
    single { GetFoodByDateUseCase(get()) }
    single<DateParserInterface> { DateParserImplementation() }
    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion(get()) }
    single { GetFoodByDateUseCase(get())}
    single { GetRandomKetoDietMealsUseCase(get()) }
    single { GetFoodByDateUseCase(get()) }
    single<DateParserInterface> { DateParserImplementation() }
    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion(get()) }
    single { GetMealByIdUI(get()) }
    single { GetMealByIdUseCase(get()) }
    single { GetSweetsWithoutEggsUseCase(get()) }
    single { GetSeaFoodMealsSortedByProteinUseCase(get()) }
    single { GetFoodByDateUI(get(),get(), get(), get()) }
    single { GetMealBySearchForNameUseCase(get())}
}