package presentation

import model.WrongInputException


fun isTheMealLikable(): Boolean {
    println("Do you like it? {y/n}")
    readLine().let {
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
    }
}

