package logic.usecase.search


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class KMPMatcherTest {

    private lateinit var kmpMatcher: KMPMatcher

    @BeforeEach
    fun setUp() {
        kmpMatcher = KMPMatcher()
    }

    @Test
    fun `buildPrefixTable should return correct prefix table for meal name`() {

        //given
        val pattern = "spaghetti"
        val expected = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

        //when
        val result = kmpMatcher.buildPrefixTable(pattern)

        //then
        assertContentEquals(expected, result)
    }

    @Test
    fun `buildPrefixTable should return correct prefix table for repeated meal name`() {
        // given
        val pattern = "pizzapizza"
        val expected = intArrayOf(0, 0, 0, 0, 0, 1, 2, 3, 4, 5)

        // when
        val result = kmpMatcher.buildPrefixTable(pattern)

        // then
        assertContentEquals(expected, result)
    }

    @Test
    fun `buildPrefixTable should return correct prefix table for Partial prefix matching`() {
        // given
        val pattern = "kabkabz"
        val expected = intArrayOf(0, 0, 0, 1, 2, 3, 0)

        // when
        val result = kmpMatcher.buildPrefixTable(pattern)

        // then
        assertContentEquals(expected, result)
    }

    @Test
    fun `buildPrefixTable should return correct prefix table for Mixed repeat and mismatch`() {
        // given
        val pattern = "saladsalx"
        val expected = intArrayOf(0, 0, 0, 0, 0, 1, 2, 3, 0)

        // when
        val result = kmpMatcher.buildPrefixTable(pattern)

        // then
        assertContentEquals(expected, result)
    }

    @Test
    fun `buildPrefixTable should return empty prefix table for empty meal name`() {
        // given
        val pattern = ""
        val expected = intArrayOf()

        // when
        val result = kmpMatcher.buildPrefixTable(pattern)

        // then
        assertContentEquals(expected, result)
    }


    @Test
    fun `isContainsPattern should return true when meal name contains pattern`() {

        //given
        val text = "Delicious Chicken Biryani"
        val pattern = "Chicken"

        //when + then
        assertTrue(kmpMatcher.isContainsPattern(pattern, text))
    }

    @Test
    fun `isContainsPattern should return false when meal name does not contain pattern`() {
        //given
        val text = "Greek Salad"
        val pattern = "Pizza"

        //when + then
        assertFalse(kmpMatcher.isContainsPattern(text, pattern))
    }

    @Test
    fun `isContainsPattern should return true when pattern is empty in food context`() {
        //given
        val text = "Tandoori Chicken"
        val pattern = ""

        //then and when
        assertTrue(kmpMatcher.isContainsPattern(pattern, text))
    }

    @Test
    fun `isContainsPattern should work with repeated food terms`() {
        //given
        val text = "Pizza Pizza Pizza"
        val pattern = "Pizza Pizza"

        //then and when
        assertTrue(kmpMatcher.isContainsPattern(pattern, text))
    }

    @Test
    fun `isContainsPattern should return false when food name is empty`() {
        //given
        val text = ""
        val pattern = "Burger"

        //when and then
        assertFalse(kmpMatcher.isContainsPattern(pattern, text))
    }
}