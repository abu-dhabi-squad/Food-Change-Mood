package util

import java.time.LocalDate

interface GetFoodByDateValidationInterface {
    fun isValidDate(date: String):Boolean
    fun isValidYear(year: Int):Boolean
    fun isValidMonth(month: Int):Boolean
    fun isValidDay(day: Int, date: LocalDate):Boolean

}