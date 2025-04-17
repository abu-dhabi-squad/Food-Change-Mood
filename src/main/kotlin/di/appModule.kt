package di

import data.FakeCsvFoodRepositoryImp
import logic.repository.FoodRepository
import logic.usecase.EasyFoodSuggestionGameUseCase
import org.koin.dsl.module

val appModule = module {
    single<FoodRepository> { FakeCsvFoodRepositoryImp() }
}