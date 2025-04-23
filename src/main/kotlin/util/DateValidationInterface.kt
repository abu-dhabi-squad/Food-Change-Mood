package util

import java.time.LocalDate

interface DateValidationInterface {
    fun isValidDate(date: String):Boolean
    fun isValidYear(year: Int):Boolean
    fun isValidMonth(month: Int):Boolean
    fun isValidDay(day: Int, daysOfTheMonth: Int): Boolean
}