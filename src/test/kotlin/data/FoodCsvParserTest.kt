package data

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecase.createFood
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FoodCsvParserTest {
    private lateinit var foodCsvParser: FoodCsvParser
    private lateinit var csvReader: FileReader
    private lateinit var foodMapper: FoodMapper

    @BeforeEach
    fun setup() {
        foodMapper = mockk(relaxed = true)
        csvReader = mockk(relaxed = true)
        foodCsvParser = FoodCsvParser(csvReader, foodMapper)
    }

    val header = "name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients"

    val row = "arriba   baked winter \"squash\" mexican style,137739,55,47892,9/16/2005," +
            "['60-minutes-or-\"\"less', 'time-to-make', 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'vegetables', 'mexican', 'easy', 'fall', 'holiday-event', 'vegetarian', 'winter', 'dietary', 'christmas', 'seasonal', 'squash']," +
            "[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0],11," +
            "['make a choice \n and proceed with recipe',\n 'depending on size of \"squash ," +
            " cut into half or fourths', 'remove \"\"seeds', 'for spicy squash , drizzle olive oil or melted butter over each cut squash piece', 'season with mexican seasoning mix ii', 'for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece', 'season with sweet mexican spice mix', 'bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin', 'be careful not to burn the squash especially if you opt to use sugar or butter', 'if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking', 'if desired , season with salt']," +
            "autumn is my favorite time of year to cook! this recipe \ncan be prepared either spicy or sweet, your choice!\ntwo of my posted mexican-inspired seasoning mix recipes are offered as suggestions.\"," +
            "['winter squash', 'mexican \"\"seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt'],7"


    @Test
    fun `parse should return empty list when get empty sequence list form csvReader `() {
        // given
        every { csvReader.readLines() } returns sequenceOf()

        // when
        val result = foodCsvParser.parse()

        // then
        assertThat(result).isEmpty()
    }


    @Test
    fun `parse should return list of two foods when get list of two foods with header form csvReader`() {
        // given
        every { csvReader.readLines() } returns sequenceOf(header , row , row)
        every { foodMapper.parseFoodRow(any()) } returns createFood()
        // when
        val result = foodCsvParser.parse()
        // then
        assertThat(result.size).isEqualTo(2)
    }


}