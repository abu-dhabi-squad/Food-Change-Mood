package di

import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.*

val uiModule = module {
    single { ItalianMealsForLargeGroupUI(get(),get()) } bind ChangeFoodMoodLauncher::class
    single { GetMealByIdUI(get(),get(),get()) } bind ChangeFoodMoodLauncher::class
    single { GuessFoodPreparationTimeGameUI(get(), get(), get(), get()) } bind ChangeFoodMoodLauncher::class
    single { GuessIngredientConsoleUI(get(), get(), get()) } bind ChangeFoodMoodLauncher::class
    single { RandomPotatoesMealsConsoleUi(get(), get(),get()) } bind ChangeFoodMoodLauncher::class
    single { GetIraqiMealsUI(get(),get()) } bind ChangeFoodMoodLauncher::class
    single { RandomKetoMealUI(get(),get(),get()) } bind ChangeFoodMoodLauncher::class
    single { GetHealthyMealsUI(get()) } bind ChangeFoodMoodLauncher::class
    single { GetRandomMealsByCountryUI(get(), get(), get()) } bind ChangeFoodMoodLauncher::class
    single { GetEasyFoodSuggestionUI(get(),get()) } bind ChangeFoodMoodLauncher::class
    single { GetMealByNameUI(get(), get(),get()) } bind ChangeFoodMoodLauncher::class
    single { GymHelperConsoleUI(get(), get(), get()) } bind ChangeFoodMoodLauncher::class
    single { SweetsWithoutEggsConsoleUI(get(),get(),get()) } bind ChangeFoodMoodLauncher::class
    single { SeaFoodMealsSortedByProteinUI(get(), get()) } bind ChangeFoodMoodLauncher::class
    single { GetHighCalorieMealForThinPeopleUI(get(),get(),get()) } bind ChangeFoodMoodLauncher::class
    single { GetFoodByDateUI(get(),get(), get(), get(),get(),get()) } bind ChangeFoodMoodLauncher::class
    single { GetUserTaste(get(), get()) }
    single { FoodChangeMoodConsoleUI(getAll(), get()) }
}