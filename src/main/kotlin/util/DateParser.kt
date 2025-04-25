package util

import java.time.LocalDate

interface DateParser {
    fun parseDateFromString(date: String): LocalDate
}