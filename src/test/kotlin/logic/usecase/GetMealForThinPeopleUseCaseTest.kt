package logic.usecase

import com.google.common.truth.Truth
import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import model.EmptyHighCalorieListException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class GetMealForThinPeopleUseCaseTest {
    private lateinit var getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase
    private val foodRepository: FoodRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMealForThinPeopleUseCase = GetMealForThinPeopleUseCase(foodRepository)
    }

    @Test
    fun `getMeal should call getFood function only once when it's called`() {
        //given
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `getMeal should throw Exception when getFood throw Exception`() {
        //given
        every { foodRepository.getFoods() } returns Result.failure(Exception())
        //when & then
        assertThrows<Exception> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when getFood function return empty list`() {
        //given
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should return item when getFood function return valid list`() {
        //given
        val data = mutableListOf(
            createMeal(id = 2000, name = "name1", description = "description1", calories = 800f),
            createMeal(id = 2100, name = "name2", description = "description2", calories = 800f),
            createMeal(id = 2200, name = "name3", description = "description3", calories = 800f),
            )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(mutableSetOf())).isIn(data)
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when data is low quality as name is null`() {
        //given
        val data = mutableListOf(createMeal(2000, null, calories = 710f, description = "description 1"))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when data is low quality as description is null`() {
        //given
        val data = mutableListOf(createMeal(2000, "name1", calories = 710f, description = null))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when data is low quality as description and name is null`() {
        //given
        val data = mutableListOf(createMeal(2000, null, calories = 710f, description = null))
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }


    @Test
    fun `getMeal should throw EmptyHighCalorieListException when data does not have high calorie meals`() {
        //given
        val data = mutableListOf(
            createMeal(2100, "name1", submittedDate = LocalDate.now(), calories = 70f, description = "description1"),
            createMeal(2200, "name2", submittedDate = LocalDate.now(), calories = 700f, description = "description2"),
            createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 200f, description = "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when data does not have high calorie meals and data is not clean`() {
        //given
        val data = mutableListOf(
            createMeal(2100, null, submittedDate = LocalDate.now(), calories = 720f, description = "description1"),
            createMeal(2200, "name2", submittedDate = LocalDate.now(), calories = 750f, description = null),
            createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 200f, description = "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(mutableSetOf())
        }
    }

    @Test
    fun `getMeal should contain all high calorie meals when there is not unliked meals and data is clean`() {
        //given
        val data = mutableListOf(
            createMeal(2100, "name1", submittedDate = LocalDate.now(), calories = 720f, description = "description1"),
            createMeal(2200, "name2", submittedDate = LocalDate.now(), calories = 710f, description = "description2"),
            createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 800f, description = "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(mutableSetOf())).isIn(data)
    }


    @Test
    fun `getMeal should not contain all high calorie meals when there is unliked meals`() {
        //given
        val unLikedList: MutableSet<Int> = mutableSetOf(2100)
        val data = mutableListOf(
            createMeal(2100, "name1", submittedDate = LocalDate.now(), calories = 720f, description = "description1"),
            createMeal(2200, "name2", submittedDate = LocalDate.now(), calories = 710f, description = "description2"),
            createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 800f, description = "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isNotEqualTo(2100)
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isIn(listOf(2200,2300))
    }

    @Test
    fun `getMeal should throw EmptyHighCalorieListException when all meals is being unliked`() {
        //given
        val unLikedList: MutableSet<Int> = mutableSetOf(2100,2200,2300)
        val data = mutableListOf(
            createMeal(2100, "name1", submittedDate = LocalDate.now(), calories = 720f, description = "description1"),
            createMeal(2200, "name2", submittedDate = LocalDate.now(), calories = 710f, description = "description2"),
            createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 800f, description = "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        assertThrows<EmptyHighCalorieListException> {
            getMealForThinPeopleUseCase.getMeal(unLikedList)
        }
    }

    @Test
    fun `getMeal shouldn't contain any meal in unliked list when some meals is being unliked`() {
        //given
        val unLikedList: MutableSet<Int> = mutableSetOf(2100,2200)
        val data = mutableListOf(
            createMeal(2100, "name1", submittedDate = LocalDate.now(), calories = 720f, description = "description1"),
            createMeal(2200, "name2", submittedDate = LocalDate.now(), calories = 710f, description = "description2"),
            createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 800f, description = "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isEqualTo(2300)
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isNotIn(unLikedList)
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList)).isEqualTo(createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 800f, description = "description3"))
    }

    @Test
    fun `getMeal shouldn't contain all liked list when some meals is being unliked`() {
        //given
        val unLikedList: MutableSet<Int> = mutableSetOf(2400,2500)
        val data = mutableListOf(
            createMeal(2100, "name1", submittedDate = LocalDate.now(), calories = 720f, description = "description1"),
            createMeal(2200, "name2", submittedDate = LocalDate.now(), calories = 710f, description = "description2"),
            createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 800f, description = "description3")
        )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isIn(listOf(2100,2200,2300))
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isNotIn(unLikedList)
    }

    @Test
    fun `getMeal shouldn't contain any meal in unliked list and low quality data when some meals is being unliked`() {
        //given
        val unLikedList: MutableSet<Int> = mutableSetOf(2100,2200)
        val data = mutableListOf(
            createMeal(2100, "name1", submittedDate = LocalDate.now(), calories = 720f, description = "description1"),
            createMeal(2200, "name2", submittedDate = LocalDate.now(), calories = 710f, description = "description2"),
            createMeal(2300, "name3", submittedDate = LocalDate.now(), calories = 800f, description = "description3"),
            createMeal(2400, null, submittedDate = LocalDate.now(), calories = 800f, description = "description4"),
            createMeal(2500, "name5", submittedDate = LocalDate.now(), calories = 800f, description = null),
            createMeal(2600, "name6", submittedDate = LocalDate.now(), calories = 20f, description = "description6"),
            createMeal(2700, "name7", submittedDate = LocalDate.now(), calories = 8000f, description = "description7"),
            )
        every { foodRepository.getFoods() } returns Result.success(data)
        //when & then
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isIn(listOf(2300,2700))
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isNotIn(unLikedList)
        Truth.assertThat(getMealForThinPeopleUseCase.getMeal(unLikedList).id).isNotIn(listOf(2400,2500,2600))
    }

}