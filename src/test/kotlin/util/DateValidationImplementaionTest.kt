package util

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import model.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate

class DateValidationImplementaionTest{
    private val dateParserInterface: DateParserInterface = mockk(relaxed = true)
    private lateinit var dateValidation: DateValidationImplementaion

    @BeforeEach
    fun setup(){
        dateValidation = DateValidationImplementaion(dateParserInterface)
    }

    @Test
    fun `isValidDate should InvalidDateFormatException throw when parseDateFromString throw InvalidDateFormatException`(){
        //given
        every { dateParserInterface.parseDateFromString(any()) } throws InvalidDateFormatException()
        //when
        assertThrows<InvalidDateFormatException> {
            dateValidation.isValidDate("2002/10-1")
        }
    }

    @Test
    fun `isValidDate should throw InvalidYearException when year from the future`() {
        //given
        val futureYear = LocalDate.now().year + 1
        val date = "$futureYear-1-1"
        every { dateParserInterface.parseDateFromString(any()) } returns LocalDate.of(futureYear,1,1)
        //when & then
        assertThrows<InvalidYearException> {
            dateValidation.isValidDate(date)
        }
    }

    @Test
    fun `isValidDate should throw InvalidYearException when year before 1000`() {
        //given
        val year = 99
        val date = "$year-1-1"
        every { dateParserInterface.parseDateFromString(any()) } returns LocalDate.of(year,1,1)
        //when & then
        assertThrows<InvalidYearException> {
            dateValidation.isValidDate(date)
        }
    }

    @Test
    fun `isValidDate should return true when date is valid`() {
        //given
        val year = 2025
        val date = "2020-2-10"
        every { dateParserInterface.parseDateFromString(any()) } returns LocalDate.of(year,1,1)
        //when & then
        Truth.assertThat(dateValidation.isValidDate(date)).isTrue()
    }

    @Test
    fun `isValidDate should throw InvalidDateException when the month is from the future`() {
        //given
        val year = LocalDate.now().year
        val month = LocalDate.now().month + 1
        val day = LocalDate.now().dayOfMonth
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(year,month,day)
        //when & then
        assertThrows<InvalidDateException> {
            dateValidation.isValidDate("$year-$month-$day")
        }
    }

    @Test
    fun `isValidDate should throw InvalidDateException when the day is from the future`() {
        //given
        val year = LocalDate.now().year
        val month = LocalDate.now().month
        val day = LocalDate.now().dayOfMonth + 1
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(year,month,day)
        //when & then
        assertThrows<InvalidDateException> {
            dateValidation.isValidDate("$year-$month-$day")
        }
    }

}