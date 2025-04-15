import di.appModule
import logic.repository.FoodRepository
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import presentation.FoodChangeMoodConsoleUI

fun main() {
     startKoin {
         modules(appModule)
     }
    val foodRepository = getKoin().get<FoodRepository>()
    FoodChangeMoodConsoleUI().start()
}