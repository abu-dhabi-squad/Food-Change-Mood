package di

import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import presentation.GuessFoodPreparationTimeGameUI
import presentation.ItalianMealsForLargeGroupUI
import java.io.File
import presentation.RandomPotatoesMealsConsoleUi

val appModule = module {
    single { GuessFoodPreparationTimeGameUI(get(),get ()) }
    single { ItalianMealsForLargeGroupUI(get()) }
    single { File("food.csv") }
    single { FoodCsvParser(get()) }
    single<FoodRepository> { CsvFoodRepositoryImp(get()) }
    single { RandomPotatoesMealsConsoleUi(get()) }
    single { FoodChangeMoodConsoleUI(get(), get(), get(), get(),get()) }
}