package data

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecase.createFood
import logic.usecase.createMealForGymHelper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.BufferedReader
import java.io.File
import java.nio.file.Path
import java.util.stream.Stream
import kotlin.math.truncate

class FoodCsvParserTest {
    private lateinit var foodCsvParser: FoodCsvParser
    private lateinit var csvFile: File
    private lateinit var foodMapper: FoodMapper

    @BeforeEach
    fun setup() {
        foodMapper = mockk()
        csvFile = mockk(relaxed = true)
        foodCsvParser = FoodCsvParser(csvFile, foodMapper)
    }

    @Test
    fun `parse should return empty list when csv file does not exist`() {
        // given
        every { csvFile.exists() } returns false

        // when
        val result = foodCsvParser.parse()

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `parse should return empty list when csv file is empty`(@TempDir tempDir: Path) {
        // given
        val file = tempDir.resolve("foods.csv").toFile()
        foodCsvParser = FoodCsvParser(file, foodMapper)
        // when
        val result = foodCsvParser.parse()

        // then
        assertThat(result).isEmpty()
    }

    @ParameterizedTest
    @MethodSource("provideTextInFileWithFoodObjectCount")
    fun `parse should return list of food when csv file contain foods text`(textInFile:String,count:Int,@TempDir tempDir: Path ) {
        // given
        val file = tempDir.resolve("foods.csv").toFile()
        file.writeText(textInFile)
        every { foodMapper.parseFoodRow(any()) } returns createFood()
        foodCsvParser = FoodCsvParser(file, foodMapper)
        // when
        val result = foodCsvParser.parse()

        // then
        assertThat(result.size).isEqualTo(count)
    }




    companion object {
        @JvmStatic
        fun provideTextInFileWithFoodObjectCount(): Stream<Arguments> = Stream.of(
            Arguments.of("name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients\n"+
                "arriba   baked winter squash \"mexican\" style,42198,1460,41531,10/3/2002,\"['weeknight\"\"', 'time-to-make', 'course', 'main-ingredient', 'preparation', 'occasion', 'low-protein', 'pies-and-tarts', 'desserts', 'fruit', '1-day-or-more', 'pies', 'dietary', 'low-sodium', 'comfort-food', 'low-in-something', 'berries', 'strawberries', 'taste-mood']\",\"[734.1, 66.0, 199.0, 10.0, 10.0, 117.0, 28.0]\",8,\"['crush vanilla wafers into fine \"\"crumbs and line a square 8\"\" x8\"\" pan', 'mix butter or margarine and sugar', 'add beaten eggs', 'spread the mixture over the wafer crumbs', 'crush strawberries and spread over sugar , egg , and butter mixture', 'cover strawberries with whipped cream', 'sprinkle with chopped nuts', 'chill 24 hours']\",\"simple but sexy. this was in my local newspaper's food section. cook time reflects refrigeration time.  i've been asked several times if this should be baked.  just to clarify, no, it is not, it is a refrigerator dessert. i'm not sure why it contains raw egg, but most tiramisu recipes do too.\",\"['vanilla wafers', 'butter', 'powdered sugar', 'eggs', 'whipping cream', 'strawberry', 'walnuts']\",7\n" +
                    "arriba   baked winter squash mexican style,67547,2970,85627,7/26/2003,\"['weeknight', 'time-to-make'," +
                    " 'course', 'main-ingredient', 'cuisine', 'preparation', 'occasion', 'north-american', 'side-dishes', 'beans', 'american', '1-day-or-more', 'oven', 'potluck', 'to-go', 'equipment']\",\"[462.4, 28.0, 214.0, 69.0, 14.0, 29.0, 23.0]\",9,\"['in a very large sauce pan cover the beans and bouillon cubes in 3 inches of water', 'bring to a boil , cover , and let sit for 1 1 / 2 hours', 'preheat oven to 350 degrees f', \"\"drain and pour beans into a 9'x11' pan\"\", 'add the rest over the ingredients , stir , and bake until all the water has evaporated , about 3 hours', 'remove from oven , cover , and let sit overnight', 'preheat oven to 350 degrees f', 'add the next 5 cups of water , and bake until all the water has evaporated , about 3 hours', 'serving suggestions: challah bread , steak , hamburgers']\",\"i'd have to say that this is a labor of love dish, but i give you my word that this recipe is better than bush's. enjoy! oh, and also this recipe is easily doubled. in fact, i think it turns out better when it is.\",\"['great northern bean', 'chicken bouillon cubes', 'dark brown sugar', 'molasses', 'cornstarch', 'onion', 'garlic powder', 'mustard powder', 'chili powder', 'salt', 'black pepper', 'bacon', 'water']\",13",
            2),
            Arguments.of("name,id,minutes,contributor_id,submitted,tags,nutrition,n_steps,steps,description,ingredients,n_ingredients\n"+
                "arriba   baked winter squash mexican style,107517,525,137696,1/3/2005,\"['time-to-make', 'main-ingredient', 'preparation', 'vegetables', 'dietary', 'greens', 'collard-greens']\",\"[315.8, 0.0, 202.0," +
                    " 9.0, 6.0, 0.0, 21.0]\",7,\"['put prepared greens in large pot', 'add water', 'bring to a boil , and boil for one hour', 'add sugar , molasses , hot sauce , whiskey , and ham hock', 'bring to a boil again , and boil for another hour', 'reduce heat and simmer for 4-6 hours', 'during the last hour of cooking , add a little salt to taste']\",my boss gave me this recipe several years ago. the recipe supposedly came from the ,\"['collard greens', 'brown sugar', 'molasses', 'hot sauce', 'whiskey', 'ham hock', 'salt']\",7\n" +
                    "arriba   baked winter squash mexican style,39959,5,37449,9/10/2002,\"['15-minutes-or-less', 'time-to-make', 'preparation', 'easy', 'number-of-servings']\",\"[8.2, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0]\",6,\"['combine herbs', 'dosage: one-half teaspoonful mixed in one-half glass of cold water , followed by a glass of hot water , one hour before each meal and upon retiring', 'this can be put in gelatin capsules', 'two no', '00 capsules would contain the required amount for one dose', 'more can be taken with beneficial results']\",\"this will prove a blessing to everyone who takes it. it is soothing and relaxing, quieting to the nerves, has many good qualities, and is perfectly harmless. you can get the ingredients from any good health food store. you could up this to 1 cup each or lessen it to 1/8 cup each, according to your preference.\",\"['gentian root', 'scullcap herb', 'burnet root', 'wood bethony', 'spearmint']\",5\n" +
                    "arriba   baked winter squash mexican style,63986,500,14664,6/6/2003,\"['weeknight', 'time-to-make', 'course', 'main-ingredient', 'preparation', 'main-dish', 'pork', 'crock-pot-slow-cooker', 'dietary', 'meat', 'pork-chops', 'equipment']\",\"[105.7, 8.0, 0.0, 26.0, 5.0, 4.0, 3.0]\",5,\"['dredge pork chops in mixture of flour , salt , dry mustard and garlic powder', 'brown in oil in a large skillet', 'place browned pork chops in a crock pot', 'add the can of soup , undiluted', 'cover and cook on low for 6-8 hours']\",here's and old standby i enjoy from time to time. it's from an old newspaper clipping i cut out years ago. very tasty.,\"['lean \"\"pork chops', 'flour', 'salt', 'dry mustard', 'garlic powder', 'oil', 'chicken rice soup']\",7",
              3
            )
        )
    }










}