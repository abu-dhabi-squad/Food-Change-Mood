package di

import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import data.FakeCsvFoodRepositoryImp
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import presentation.ItalianMealsForLargeGroupUI
import java.io.File
import presentation.RandomPotatoesMealsConsoleUi
import presentation.getIraqiMealsUseCaseUI

val appModule = module {
    single { ItalianMealsForLargeGroupUI(get()) }
    single { File("food.csv") }
    single { FoodCsvParser(get()) }
    single<FoodRepository> { CsvFoodRepositoryImp(get()) }
    single { RandomPotatoesMealsConsoleUi(get()) }
    single { FoodChangeMoodConsoleUI(get(),get()) }
    single { getIraqiMealsUseCaseUI(get()) }
}