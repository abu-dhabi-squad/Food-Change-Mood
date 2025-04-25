package data

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import model.Food
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.DateParser
import java.time.LocalDate
import java.time.format.DateTimeParseException

class FoodMapperTest {
    private lateinit var foodMapper: FoodMapper
    private lateinit var dateParserInterface: DateParser

    @BeforeEach
    fun setup() {
        dateParserInterface = mockk(relaxed = true)
        foodMapper = FoodMapper(dateParserInterface)
    }

    private val validFoodData = listOf(
        "arriba   baked winter squash mexican style", "137739", "55", "47892", "9/16/2005",
        "['60-minutes-or-less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'vegetables', 'mexican', 'easy', 'fall', 'holiday-event', 'vegetarian', 'winter', 'dietary', 'christmas', 'seasonal', 'squash']",
        "[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]", "11",
        "['make a choice and proceed with recipe', 'depending on size of squash , cut into half or fourths', 'remove seeds', 'for spicy squash , drizzle olive oil or melted butter over each cut squash piece', 'season with mexican seasoning mix ii', 'for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece', 'season with sweet mexican spice mix', 'bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin', 'be careful not to burn the squash especially if you opt to use sugar or butter', 'if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking', 'if desired , season with salt']",
        "autumn is my favorite time of year to cook! this recipe \ncan be prepared either spicy or sweet, your choice!\ntwo of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
        "['winter squash', 'mexican seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt']", "7"
    )

    private fun expectedFood(submittedDate: LocalDate?) = Food(
        name = "arriba   baked winter squash mexican style", id = 137739, minutes = 55, submittedDate = submittedDate,
        tags = listOf(
            "60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine",
            "preparation", "occasion", "north-american", "side-dishes", "vegetables",
            "mexican", "easy", "fall", "holiday-event", "vegetarian", "winter",
            "dietary", "christmas", "seasonal", "squash"
        ),
        nutrition = Nutrition(51.5f, 0.0f, 13.0f, 0.0f, 2.0f, 0.0f, 4.0f),
        steps = listOf(
            "make a choice and proceed with recipe",
            "depending on size of squash , cut into half or fourths",
            "remove seeds",
            "for spicy squash , drizzle olive oil or melted butter over each cut squash piece",
            "season with mexican seasoning mix ii",
            "for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece",
            "season with sweet mexican spice mix",
            "bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin",
            "be careful not to burn the squash especially if you opt to use sugar or butter",
            "if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking",
            "if desired , season with salt"
        ),
        description = "autumn is my favorite time of year to cook! this recipe \ncan be prepared either spicy or sweet, your choice!\ntwo of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
        ingredients = listOf("winter squash", "mexican seasoning", "mixed spice", "honey", "butter", "olive oil", "salt"),
    )

    @Test
    fun `should map text to food object when list is correct`() {
        // given
        val date = LocalDate.of(2005, 9, 16)
        every { dateParserInterface.parseDateFromString("9/16/2005") } returns date

        // when
        val result = foodMapper.parseFoodRow(validFoodData)

        // then
        assertThat(result).isEqualTo(expectedFood(date))
    }

    @Test
    fun `should map with null date when date parsing fails`() {
        // given
        val foodDataWithInvalidDate = validFoodData.toMutableList().apply { set(4, "9//2005") }
        every { dateParserInterface.parseDateFromString(any()) } throws DateTimeParseException("", "", 0)

        // when
        val result = foodMapper.parseFoodRow(foodDataWithInvalidDate)

        // then
        assertThat(result).isEqualTo(expectedFood(null))
        assertThat(result.submittedDate).isNull()
    }

    @Test
    fun `should throw IndexOutOfBoundsException when list is empty`() {
        // given
        val foodData = emptyList<String>()
        // when && then
        assertThrows<IndexOutOfBoundsException> {
            foodMapper.parseFoodRow(foodData)
        }
    }
}
