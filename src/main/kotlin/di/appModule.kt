package di

import data.FakeCsvFoodRepositoryImp
import logic.repository.FoodRepository
import org.koin.dsl.module
import presentation.RandomPotatoesMealsConsoleUi

val appModule = module {
    single<FoodRepository> { FakeCsvFoodRepositoryImp() }
    single{RandomPotatoesMealsConsoleUi(get())}
}