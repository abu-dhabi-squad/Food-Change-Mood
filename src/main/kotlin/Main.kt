import di.appModule
import di.readerModule
import di.uiModule
import di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import presentation.FoodChangeMoodConsoleUI

fun main() {
    startKoin {
        modules(appModule, useCaseModule, readerModule, uiModule)
    }
    val foodChangeMoodConsoleUI: FoodChangeMoodConsoleUI = getKoin().get()
    foodChangeMoodConsoleUI.launchUI()

}