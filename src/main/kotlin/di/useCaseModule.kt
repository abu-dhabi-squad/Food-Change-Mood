package di

import logic.GetItalianMealsForLargeGroupUseCase
import logic.usecase.*
import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetMealBySearchForNameUseCase
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
    single { GetFoodByDateUseCase(get())}
    single { GetMealByIdUI(get()) }
    single { GetMealByIdUseCase(get()) }
    single { GetSweetsWithoutEggsUseCase(get()) }
    single { GetSeaFoodMealsSortedByProteinUseCase(get()) }
    single { GetFoodByDateUI(get(),get(), get(), get()) }
    single { GetMealBySearchForNameUseCase(get())}
}