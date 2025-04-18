package di

import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import logic.repository.FoodRepository
import logic.usecase.MealSearchByNameUseCase
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import presentation.GymHelperConsoleUI
import presentation.*
import util.DateParserImplementation
import util.DateParserInterface
import util.GetFoodByDateValidationImplementaion
import util.GetFoodByDateValidationInterface
import java.io.File

val appModule = module {
    single { GuessFoodPreparationTimeGameUI(get(),get ()) }
    single { ItalianMealsForLargeGroupUI(get()) }
    single { File("food.csv") }
    single { FoodCsvParser(get(), get()) }
    single<FoodRepository> { CsvFoodRepositoryImp(get()) }
    single { RandomPotatoesMealsConsoleUi(get()) }
    single { getIraqiMealsUseCaseUI(get()) }
    single { GetHealthyMealsConsoleUI(get()) }
    single { GymHelperConsoleUI(get()) }
    single { SeaFoodMealsSortedByProteinUI (get())}
    single<DateParserInterface> { DateParserImplementation() }
    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion(get()) }
    single { FoodChangeMoodConsoleUI(get(), get(), get(), get(),get(),get(),get(),get(),get(),get(),get(), get()) }
}