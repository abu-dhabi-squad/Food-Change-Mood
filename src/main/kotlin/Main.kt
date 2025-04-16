import di.appModule
import di.uiModule
import di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import presentation.FoodChangeMoodConsoleUI

fun main() {
    startKoin {
        modules(appModule, useCaseModule, uiModule)
    }
    FoodChangeMoodConsoleUI(getKoin().get()).start()
}