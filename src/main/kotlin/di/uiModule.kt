package di

import org.koin.dsl.module
import presentation.*

val uiModule = module {
    single { GetMealByIdUI(get(),get()) }
    single { GuessFoodPreparationTimeGameUI(get(), get(), get(),get()) }
    single { ItalianMealsForLargeGroupUI(get()) }
    single { GuessIngredientConsoleUI(get(), get()) }
    single { RandomPotatoesMealsConsoleUi(get(), get(),get()) }
    single { GetIraqiMealsUI(get()) }
    single { RandomKetoMealUI(get()) }
    single { GetHealthyMealsUI(get()) }
    single { GetRandomMealsByCountryUI(get(), get() , get()) }
    single { GetMealByNameUI(get(), get(),get()) }
    single { GymHelperConsoleUI(get(), get(), get()) }
    single { SweetsWithoutEggsConsoleUI(get()) }
    single { SeaFoodMealsSortedByProteinUI(get()) }
    single { GetHighCalorieMealForThinPeopleUI(get()) }
    single { GetFoodByDateUI(get(),get(), get(), get(),get()) }
    single { GetEasyFoodSuggestionUI(get()) }
    single { FoodChangeMoodConsoleUI(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}