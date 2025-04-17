package di

import data.FakeCsvFoodRepositoryImp
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import presentation.GymHelperConsoleUI

val appModule = module {
    single<FoodRepository> { FakeCsvFoodRepositoryImp() }
    single { GymHelperConsoleUI(get()) }
    single { FoodChangeMoodConsoleUI(get(), get()) }
}