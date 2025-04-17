package di

import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.MealSearchByNameUseCase
import org.koin.dsl.module


val useCaseModule = module {
    single { GetMealForThinPeopleUseCase(get()) }
    single { MealSearchByNameUseCase(get())}
}
