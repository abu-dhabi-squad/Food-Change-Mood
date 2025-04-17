package di

import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import presentation.GuessFoodPreparationTimeGameUI
import java.io.File

val appModule = module {
    single { GuessFoodPreparationTimeGameUI(get(),get ()) }

    single { FoodChangeMoodConsoleUI(get(),get ()) }
    single { File("food.csv") }
    single { FoodCsvParser(get()) }
    single<FoodRepository> { CsvFoodRepositoryImp(get()) }
}