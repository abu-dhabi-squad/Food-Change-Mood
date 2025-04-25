package presentation

import presentation.ui_io.InputReader
import kotlin.system.exitProcess

class FoodChangeMoodConsoleUI(
    private val featuresUi: List<ChangeFoodMoodLauncher>,
    private val reader: InputReader
) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 0

    override val name: String
        get() = "Main Menu"

    override fun launchUI() {
        sortMenu()
        showWelcome()
        presentFeature()
    }

    private fun showWelcome() {
        println("Welcome to Food Change MoodConsole ")
    }

    private fun presentFeature() {
        showOptions()
        print("Enter your choice: ")
        val input = reader.readInt()
        if (input != null && input in 1 .. featuresUi.size) {
            featuresUi.find { it.id == input }?.launchUI()
        } else if(input == 0) {
            exitProcess(0)
        } else {
            println("Invalid input")
        }
        presentFeature()
    }

    private fun showOptions() {
        println()
        println("╔══════════════════════════════════════╗")
        println("║       Food Change Mood Console       ║")
        println("╠══════════════════════════════════════╣")
        featuresUi.sortedBy { it.id }.forEach { printFeatureLine(it) }
        println("║  0.  Exit                            ║")
        println("╚══════════════════════════════════════╝")
    }

    private fun printFeatureLine(featureUi: ChangeFoodMoodLauncher) {
        println("║ ${(featureUi.id).toString().padStart(2, ' ')}.  ${featureUi.name.padEnd(32)}║")
    }

    private fun sortMenu() {
        featuresUi.sortedBy { it.id }
    }
}
