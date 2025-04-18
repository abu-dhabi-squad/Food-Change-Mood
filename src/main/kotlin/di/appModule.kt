package di

import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import logic.repository.FoodRepository
import org.koin.core.scope.get
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import presentation.GuessFoodPreparationTimeGameUI
import presentation.ItalianMealsForLargeGroupUI
import presentation.RandomKetoMealUI
import presentation.*
import java.io.File

val appModule = module {
    single { GuessFoodPreparationTimeGameUI(get(),get ()) }
    single { ItalianMealsForLargeGroupUI(get()) }
    single { File("food.csv") }
    single { FoodCsvParser(get(), get()) }
    single<FoodRepository> { CsvFoodRepositoryImp(get()) }
    single { RandomPotatoesMealsConsoleUi(get()) }
    single { getIraqiMealsUseCaseUI(get()) }
    single { RandomKetoMealUI (get(),get())}
    single { GetHealthyMealsConsoleUI(get()) }
    single { FoodChangeMoodConsoleUI(get(), get(), get(), get(),get(),get(),get(),get(),get(),get(),get()) }
}