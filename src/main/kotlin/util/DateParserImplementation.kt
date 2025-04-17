package util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateParserImplementation: DateParserInterface {
    override fun parseDateFromString(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}