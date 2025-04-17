package di

import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import java.io.File
import presentation.RandomPotatoesMealsConsoleUi

val appModule = module {
    single { File("food.csv") }
    single { FoodCsvParser(get()) }
    single<FoodRepository> { CsvFoodRepositoryImp(get()) }
    single { FoodChangeMoodConsoleUI(get(), get()) }
    single { RandomPotatoesMealsConsoleUi(get()) }
}