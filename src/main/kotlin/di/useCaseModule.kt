package di

import logic.usecase.GetItalianMealsForLargeGroupUseCase
import logic.usecase.GetIraqiMealsUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GetRandomMealsByCountryUseCase
import logic.usecase.GetRandomPotatoesMealsUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import logic.usecase.GetHealthyMealsUseCase
import logic.usecase.GuessIngredientUseCase
import logic.usecase.*
import org.koin.dsl.module

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
    single { GetFoodByDateUseCase(get())}
    single { GetRandomKetoDietMealsUseCase(get()) }
    single { GetFoodByDateUseCase(get()) }
    single { GetMealByIdUseCase(get()) }
    single { GetSweetsWithoutEggsUseCase(get()) }
    single { GetSeaFoodMealsSortedByProteinUseCase(get()) }
    single { GetEasyFoodSuggestionGameUseCase(get()) }
    single { GetMealBySearchForNameUseCase(get())}
}