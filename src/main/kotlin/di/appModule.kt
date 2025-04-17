package di

import data.FakeCsvFoodRepositoryImp
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.FoodChangeMoodConsoleUI
import presentation.GuessFoodPreparationTimeGameUI

val appModule = module {
    single<FoodRepository> { FakeCsvFoodRepositoryImp() }
    single { GuessFoodPreparationTimeGameUI(get(),get ()) }

    single { FoodChangeMoodConsoleUI(get(),get ()) }
}