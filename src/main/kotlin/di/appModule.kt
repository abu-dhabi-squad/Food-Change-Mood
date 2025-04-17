package di

import data.FakeCsvFoodRepositoryImp
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.ItalianMealsForLargeGroupUI

val appModule = module {
    single<FoodRepository> { FakeCsvFoodRepositoryImp() }
    single { ItalianMealsForLargeGroupUI(get()) }
}