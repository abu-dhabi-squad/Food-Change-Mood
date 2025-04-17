package di

import data.FakeCsvFoodRepositoryImp
import logic.repository.FoodRepository
import logic.usecase.GetIraqiMealsUseCase
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI

val appModule = module {
    single<FoodRepository> { FakeCsvFoodRepositoryImp() }
    single { FoodChangeMoodConsoleUI(get(),get()) }
}