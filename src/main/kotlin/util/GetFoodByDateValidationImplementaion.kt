package util

import model.InvalidDateFormateException
import model.InvalidDayException
import model.InvalidMonthException
import model.InvalidYearException
import java.time.LocalDate

class GetFoodByDateValidationImplementaion(
    private val dateParserInterface: DateParserInterface
): GetFoodByDateValidationInterface
{
    override fun isValidDate(date: String): Boolean {
        val formatedDate: LocalDate
        try { formatedDate = dateParserInterface.parseDateFromString(date)}catch (e: Exception){throw InvalidDateFormateException()}

        isValidYear(formatedDate.year)
        isValidMonth(formatedDate.monthValue)
        isValidDay(formatedDate.dayOfMonth,formatedDate)
        return true
    }

    override fun isValidYear(year: Int): Boolean {
        if (year in 1000..LocalDate.now().year) return true else throw InvalidYearException()
    }

    override fun isValidMonth(month: Int): Boolean {
        if (month in 1..12) return true else  throw InvalidMonthException()
    }

    override fun isValidDay(day: Int, date:LocalDate): Boolean {
        if(day in 1..date.lengthOfMonth() && !date.isAfter(LocalDate.now()))
            return true
        else throw InvalidDayException()
    }
}