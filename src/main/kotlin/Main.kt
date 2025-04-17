import di.appModule
import di.useCaseModule
import logic.repository.FoodRepository
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import presentation.FoodChangeMoodConsoleUI

fun main() {
     startKoin {
         modules(appModule, useCaseModule)
     }
    val foodRepository = getKoin().get<FoodRepository>()
    val ui:FoodChangeMoodConsoleUI= getKoin().get()
    ui.start()
}