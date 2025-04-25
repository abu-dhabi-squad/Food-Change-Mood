package logic.usecase.search

class KMPMatcher : SearchAlgorithm {

    override fun isContainsPattern(pattern: String, text: String): Boolean {
        if (pattern.isEmpty()) return true
        if (text.isEmpty()) return false

        val prefixTable = buildPrefixTable(pattern)
        var textIndex = 0
        var patternIndex = 0

        while (textIndex < text.length) {
            if (text[textIndex] == pattern[patternIndex]) {
                textIndex++
                patternIndex++
                if (patternIndex == pattern.length) return true
            } else if (patternIndex != 0) {
                patternIndex = prefixTable[patternIndex - 1]
            } else {
                textIndex++
            }
        }
        return false
    }

    fun buildPrefixTable(pattern: String): IntArray {
        val table = IntArray(pattern.length)
        var prefixLength = 0
        var indexOfCurrentChar = 1

        while (indexOfCurrentChar < pattern.length) {
            when {
                pattern[indexOfCurrentChar] == pattern[prefixLength] -> {
                    prefixLength++
                    table[indexOfCurrentChar] = prefixLength
                    indexOfCurrentChar++
                }
                prefixLength != 0 -> {
                    prefixLength = table[prefixLength - 1]
                }
                else -> {
                    table[indexOfCurrentChar] = 0
                    indexOfCurrentChar++
                }
            }
        }
        return table
    }

}