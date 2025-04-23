package presentation

import model.Food
import model.Nutrition
import model.WrongInputException
import java.time.LocalDate

fun isLikedMeal(): Boolean {
    println("Do you like it? {y/n}")
    readlnOrNull()?.let {
        when {
            it.equals("y", true) -> {
                return true
            }

            it.equals("n", true) -> {
                return false
            }

            else -> {
                throw WrongInputException()
            }
        }
    } ?: throw WrongInputException()
}

