package di

import org.koin.dsl.module
import presentation.*

val uiModule = module {
    single { ItalianMealsForLargeGroupUI(get(),get()) }
    single { GetUserTaste(get(), get()) }
    single { GetMealByIdUI(get(),get(),get()) }
    single { GuessFoodPreparationTimeGameUI(get(), get(), get(), get()) }
    single { GuessIngredientConsoleUI(get(), get(), get()) }
    single { RandomPotatoesMealsConsoleUi(get(), get(),get()) }
    single { GetIraqiMealsUI(get(),get()) }
    single { RandomKetoMealUI(get()) }
    single { GetHealthyMealsUI(get()) }
    single { GetRandomMealsByCountryUI(get(), get() , get()) }
    single { GetMealByNameUI(get(), get()) }
    single { GymHelperConsoleUI(get(), get(), get()) }
    single { SweetsWithoutEggsConsoleUI(get()) }
    single { SeaFoodMealsSortedByProteinUI(get(), get()) }
    single { GetHighCalorieMealForThinPeopleUI(get(),get(),get()) }
    single { GetFoodByDateUI(get(),get(), get(), get(),get(),get()) }
    single { GetEasyFoodSuggestionUI(get(),get()) }
    single { FoodChangeMoodConsoleUI(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }  
}