import data.FakeCsvFoodRepositoryImp
import di.appModule
import di.useCaseModule
import logic.repository.FoodRepository
import logic.usecase.GetMealForThinPeopleUseCase
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import presentation.FoodChangeMoodConsoleUI

fun main() {
     startKoin {
         modules(appModule, useCaseModule)
     }

    val foodChangeMoodConsoleUI: FoodChangeMoodConsoleUI = getKoin().get()
    foodChangeMoodConsoleUI.start()
}