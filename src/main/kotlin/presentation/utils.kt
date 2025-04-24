package presentation

import model.WrongInputException

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

