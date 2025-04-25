package util

import model.*
import java.time.LocalDate

class DateValidationImplementaion(
    private val dateParserInterface: DateParser
): DateValidation
{
    override fun isValidDate(date: String): Boolean {
        val formatedDate: LocalDate
        try { formatedDate = dateParserInterface.parseDateFromString(date)}
        catch (e: Exception){ throw InvalidDateFormatException()}

        isValidYear(formatedDate.year)

        if(formatedDate.isAfter(LocalDate.now())) throw InvalidDateException()

        return true
    }

    private fun isValidYear(year: Int): Boolean {
        if (year in 1000..LocalDate.now().year) return true else throw InvalidYearException()
    }

}