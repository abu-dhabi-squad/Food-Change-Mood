package di

import data.FakeCsvFoodRepositoryImp
import data.FoodCsvParser
import logic.repository.FoodRepository
import logic.usecase.GetIraqiMealsUseCase
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import presentation.getIraqiMealsUseCaseUI

val appModule = module {
    single<FoodRepository> { FakeCsvFoodRepositoryImp() }
    single { FoodChangeMoodConsoleUI(get(),get()) }
    single { getIraqiMealsUseCaseUI(get()) }
}