package di

import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { FoodCsvParser(get()) }
    single<CsvFoodRepositoryImp> { CsvFoodRepositoryImp(get()) }
    single { FoodChangeMoodConsoleUI(get()) }
}