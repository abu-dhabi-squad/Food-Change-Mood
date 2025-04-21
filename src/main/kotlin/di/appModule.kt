package di

import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import data.FoodMapper
import data.FoodParser
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.ui_io.ConsolePrinter
import presentation.ui_io.Printer
import util.DateParserImplementation
import util.DateParserInterface
import util.GetFoodByDateValidationImplementaion
import util.GetFoodByDateValidationInterface
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single<FoodParser> { FoodCsvParser(get(), get()) }
    single<DateParserInterface> { DateParserImplementation() }
    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion(get()) }
    single<FoodRepository> { CsvFoodRepositoryImp(get()) }
    single<DateParserInterface> { DateParserImplementation() }
    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion(get()) }
    single<DateParserInterface> { DateParserImplementation() }
    single<GetFoodByDateValidationInterface> { GetFoodByDateValidationImplementaion(get()) }
    single<Printer> { ConsolePrinter() }
    single { FoodMapper(get()) }
}