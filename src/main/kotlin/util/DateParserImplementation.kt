package util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateParserImplementation: DateParserInterface {
    override fun parseDateFromString(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")
        return LocalDate.parse(date, formatter)
    }
}