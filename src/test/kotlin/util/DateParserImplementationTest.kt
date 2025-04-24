package util

import com.google.common.truth.Truth
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeParseException

class DateParserImplementationTest{

    private lateinit var dateParserImplementation: DateParserImplementation

    @BeforeEach
    fun setup(){
        dateParserImplementation = DateParserImplementation()
    }

    @Test
    fun `parseDateFromString should return object of local date when the input is valid`(){
        //given
        val date = "2020-2-10"
        //when
        val res = dateParserImplementation.parseDateFromString(date)
        //then
        Truth.assertThat(res).isEqualTo(LocalDate.of(2020,2,10))
    }

    @Test
    fun `parseDateFromString should throw DateTimeParseException when the input can not be parsed`(){
        //given
        val date = "2020/2-10"
        //when & then
        org.junit.jupiter.api.assertThrows<DateTimeParseException> {
            dateParserImplementation.parseDateFromString(date)
        }
    }

    @Test
    fun `parseDateFromString should throw DateTimeParseException when the month is less than 1`(){
        //given
        val date = "2020-0-10"
        //when & then
        org.junit.jupiter.api.assertThrows<DateTimeParseException> {
            dateParserImplementation.parseDateFromString(date)
        }
    }

    @Test
    fun `parseDateFromString should throw DateTimeParseException when the month is more than 12`(){
        //given
        val date = "2020-13-10"
        //when & then
        org.junit.jupiter.api.assertThrows<DateTimeParseException> {
            dateParserImplementation.parseDateFromString(date)
        }
    }

    @Test
    fun `parseDateFromString should throw DateTimeParseException when the day is less than 1`(){
        //given
        val date = "2020-12-0"
        //when & then
        org.junit.jupiter.api.assertThrows<DateTimeParseException> {
            dateParserImplementation.parseDateFromString(date)
        }
    }

    @Test
    fun `parseDateFromString should throw DateTimeParseException when the day is more than 31`(){
        //given
        val date = "2020-12-32"
        //when & then
        org.junit.jupiter.api.assertThrows<DateTimeParseException> {
            dateParserImplementation.parseDateFromString(date)
        }
    }

    @Test
    fun `parseDateFromString should return date of the last day in month when the input' day is more than the last day of the month`(){
        //given
        val date = "2025-2-29"
        val res = LocalDate.of(2025,2,28)
        //when & then
        Truth.assertThat(dateParserImplementation.parseDateFromString(date)).isEqualTo(res)
    }

}