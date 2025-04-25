package logic.usecase.search

interface StringSearchAlgorithm {

    fun isContainsPattern(pattern: String, text: String): Boolean

}