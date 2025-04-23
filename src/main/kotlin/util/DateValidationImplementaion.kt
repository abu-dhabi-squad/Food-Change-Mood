package util

import model.*
import java.time.LocalDate

class DateValidationImplementaion(
    private val dateParserInterface: DateParserInterface
): DateValidationInterface
{
    override fun isValidDate(date: String): Boolean {
        val formatedDate: LocalDate
        try { formatedDate = dateParserInterface.parseDateFromString(date)}catch (e: Exception){throw InvalidDateFormatException()}

        if(formatedDate.isAfter(LocalDate.now())) throw InvalidDateException()

        isValidYear(formatedDate.year)
        isValidMonth(formatedDate.monthValue)
        isValidDay(formatedDate.dayOfMonth, formatedDate.lengthOfMonth())
        return true
    }

    override fun isValidYear(year: Int): Boolean {
        if (year in 1000..LocalDate.now().year) return true else throw InvalidYearException()
    }

    override fun isValidMonth(month: Int): Boolean {
        if (month in 1..12) return true else  throw InvalidMonthException()
    }

    override fun isValidDay(day: Int, daysOfTheMonth: Int): Boolean{
        if (day in 1..daysOfTheMonth) return true else throw InvalidDayException()
    }

}