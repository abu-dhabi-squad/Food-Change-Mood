package util

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import model.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class DateValidationImplementaionTest{
    private val dateParserInterface: DateParserInterface = mockk(relaxed = true)
    private val getFoodByDateValidation = spyk(DateValidationImplementaion(dateParserInterface))

    @Test
    fun `isValidYear should throw InvalidYearException when year from the future`() {
        //given
        val futureYear = LocalDate.now().year + 1
        //when & then
        assertThrows<InvalidYearException> {
            getFoodByDateValidation.isValidYear(futureYear)
        }
    }

    @Test
    fun `isValidYear should throw InvalidYearException when year before 1000`() {
        //given
        val year = 99
        //when & then
        assertThrows<InvalidYearException> {
            getFoodByDateValidation.isValidYear(year)
        }
    }

    @Test
    fun `isValidYear should return true when year is valid`() {
        //given
        val year = 2002
        //when & then
        Truth.assertThat(getFoodByDateValidation.isValidYear(year)).isTrue()
    }

    @Test
    fun `isValidMonth should throw InvalidMonthException when month is less than 1`() {
        //given
        val month = 0
        //when & then
        assertThrows<InvalidMonthException> {
            getFoodByDateValidation.isValidMonth(month)
        }
    }

    @Test
    fun `isValidMonth should throw InvalidMonthException when month is more than 12`() {
        //given
        val month = 13
        //when & then
        assertThrows<InvalidMonthException> {
            getFoodByDateValidation.isValidMonth(month)
        }
    }

    @Test
    fun `isValidMonth should return true when month is valid`() {
        //given
        val month = 12
        //when & then
        Truth.assertThat(getFoodByDateValidation.isValidMonth(month)).isTrue()
    }

    @Test
    fun `isValidDay should throw InvalidDayException when day is less than 1`() {
        //given
        val day = 0
        val daysOfTheMonth = LocalDate.now().lengthOfMonth()
        //when & then
        assertThrows<InvalidDayException> {
            getFoodByDateValidation.isValidDay(day, daysOfTheMonth)
        }
    }

    @Test
    fun `isValidDay should throw InvalidDayException when day is more than the last day of the month`() {
        //given
        val day = LocalDate.now().lengthOfMonth() + 1
        val daysOfTheMonth = LocalDate.now().lengthOfMonth()
        //when & then
        assertThrows<InvalidDayException> {
            getFoodByDateValidation.isValidDay(day, daysOfTheMonth)
        }
    }

    @Test
    fun `isValidDay should return true when day is valid`() {
        //given
        val day = 12
        val daysOfTheMonth = LocalDate.now().lengthOfMonth()
        //when & then
        Truth.assertThat(getFoodByDateValidation.isValidDay(day, daysOfTheMonth)).isTrue()
    }


    @Test
    fun `isValidDate should throw InvalidDateFormatException when dateParserInterface throw Exception`() {
        //given
        every{dateParserInterface.parseDateFromString(any())} throws Exception()
        //when & then
        assertThrows<InvalidDateFormatException> {
            getFoodByDateValidation.isValidDate("2002-12-12")
        }
    }

    @Test
    fun `isValidDate should throw InvalidDateException when the date is from the future`() {
        //given
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(LocalDate.now().year,LocalDate.now().month,LocalDate.now().dayOfMonth + 1)
        every{getFoodByDateValidation.isValidYear(any())} returns true
        every{getFoodByDateValidation.isValidMonth(any())} returns true
        every{getFoodByDateValidation.isValidDay(any(),any())} returns true
        //when & then
        assertThrows<InvalidDateException> {
            getFoodByDateValidation.isValidDate("2002-12-12")
        }
    }

    @Test
    fun `isValidDate should throw InvalidYearException when isValidYear throw InvalidYearException`() {
        //given
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(2002,12,12)
        every{getFoodByDateValidation.isValidYear(any())} throws InvalidYearException()
        every{getFoodByDateValidation.isValidMonth(any())} returns true
        every{getFoodByDateValidation.isValidDay(any(),any())} returns true
        //when & then
        assertThrows<InvalidYearException> {
            getFoodByDateValidation.isValidDate("2002-12-12")
        }
    }

    @Test
    fun `isValidDate should throw InvalidMonthException when isValidMonth throw InvalidMonthException`() {
        //given
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(2002,12,12)
        every{getFoodByDateValidation.isValidMonth(any())} throws InvalidMonthException()
        every{getFoodByDateValidation.isValidYear(any())} returns true
        every{getFoodByDateValidation.isValidDay(any(),any())} returns true
        //when & then
        assertThrows<InvalidMonthException> {
            getFoodByDateValidation.isValidDate("2002-12-12")
        }
    }

    @Test
    fun `isValidDate should throw InvalidDayException when isValidDay throw InvalidDayException`() {
        //given
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(2002,12,12)
        every{getFoodByDateValidation.isValidMonth(any())} returns true
        every{getFoodByDateValidation.isValidYear(any())} returns true
        every{getFoodByDateValidation.isValidDay(any(),any())} throws InvalidDayException()
        //when & then
        assertThrows<InvalidDayException> {
            getFoodByDateValidation.isValidDate("2002-12-12")
        }
    }

    @Test
    fun `isValidDate should return true when the date is valid`() {
        //given
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(2002,12,12)
        //when & then
        Truth.assertThat(getFoodByDateValidation.isValidDate("2002-12-12")).isTrue()
    }
}