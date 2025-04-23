package util

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import model.InvalidDateFormatException
import model.InvalidDayException
import model.InvalidMonthException
import model.InvalidYearException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class GetFoodByDateValidationImplementaionTest{
    private val dateParserInterface: DateParserInterface = mockk(relaxed = true)
    private val getFoodByDateValidation = spyk(GetFoodByDateValidationImplementaion(dateParserInterface))

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
    fun `isValidDay should throw InvalidDayException when the date is after the current day`() {
        //given
        val date = LocalDate.of(LocalDate.now().year,LocalDate.now().month,LocalDate.now().dayOfMonth+1)
        //when & then
        assertThrows<InvalidDayException> {
            getFoodByDateValidation.isValidDay(date)
        }
    }

    @Test
    fun `isValidDay should return true when day is valid`() {
        //given
        val date = LocalDate.of(2002,7,2)
        //when & then
        Truth.assertThat(getFoodByDateValidation.isValidDay(date)).isTrue()
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
    fun `isValidDate should throw InvalidDayException when isValidDay throw InvalidDayException`() {
        //given
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(2002,12,12)
        every{getFoodByDateValidation.isValidDay(any())} throws InvalidDayException()
        every{getFoodByDateValidation.isValidYear(any())} returns true
        every{getFoodByDateValidation.isValidMonth(any())} returns true
        //when & then
        assertThrows<InvalidDayException> {
            getFoodByDateValidation.isValidDate("2002-12-12")
        }
    }

    @Test
    fun `isValidDate should throw InvalidYearException when isValidYear throw InvalidYearException`() {
        //given
        every{dateParserInterface.parseDateFromString(any())} returns LocalDate.of(2002,12,12)
        every{getFoodByDateValidation.isValidYear(any())} throws InvalidYearException()
        every{getFoodByDateValidation.isValidMonth(any())} returns true
        every{getFoodByDateValidation.isValidDay(any())} returns true
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
        every{getFoodByDateValidation.isValidDay(any())} returns true

        //when & then
        assertThrows<InvalidMonthException> {
            getFoodByDateValidation.isValidDate("2002-12-12")
        }
    }
}