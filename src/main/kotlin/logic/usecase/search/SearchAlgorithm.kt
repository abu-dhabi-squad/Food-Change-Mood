package logic.usecase.search

interface SearchAlgorithm {

    fun isContainsPattern(pattern: String, text: String): Boolean

}