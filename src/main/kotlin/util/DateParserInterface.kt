package util

import java.time.LocalDate

interface DateParserInterface {
    fun parseDateFromString(date: String): LocalDate
}