import di.appModule
import di.useCaseModule
import logic.repository.FoodRepository
import logic.usecase.GetRandomPotatoesMealsUseCase
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import presentation.FoodChangeMoodConsoleUI
import presentation.RandomPotatoesMealsConsoleUi

fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }
    val foodRepository = getKoin().get<FoodRepository>()
    val randomPotatoesMealsConsoleUi = getKoin().get<RandomPotatoesMealsConsoleUi>()
    FoodChangeMoodConsoleUI(
        randomPotatoesMealsConsoleUi
    ).start()
}