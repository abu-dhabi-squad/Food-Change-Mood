package data

import model.Food
import java.io.File


class FoodCsvParser(
    private val csvFile: File,
    private val foodMapper: FoodMapper
) : FoodParser {

    override fun parse(): List<Food> {
        return if (csvFile.exists()) {
            getFoodsList()
        } else emptyList()
    }

    private fun getFoodsList(): List<Food> {
        return parseCsvLines().drop(1)
            .asSequence()
            .map {  foodMapper.parseFoodRow(it) }
            .toList()
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val current = StringBuilder()
        var insideQuotes = false
        var i = 0

        while (i < line.length) {
            val c = line[i]
            when (c) {
                '"' -> {
                    if (insideQuotes && i + 1 < line.length && line[i + 1] == '"') {
                        current.append('"')
                        i++
                    } else {
                        insideQuotes = !insideQuotes
                    }
                }

                ',' -> {
                    if (insideQuotes) current.append(c)
                    else {
                        result.add(current.toString())
                        current.clear()
                    }
                }

                else -> current.append(c)
            }
            i++
        }
        result.add(current.toString())
        return result
    }

    private fun parseCsvLines(): List<List<String>> {
        val rows = mutableListOf<List<String>>()
        var buffer = StringBuilder()
        var insideQuotes = false
         csvFile.forEachLine { rawLine ->

            buffer.appendLine(rawLine)
            val quoteCount = rawLine.count { it == '"' }
            insideQuotes =
                if (insideQuotes) quoteCount % 2 == 0 else quoteCount % 2 != 0

            if (!insideQuotes) {
                val parsed = parseCsvLine(buffer.toString().trim())
                rows.add(parsed)
                buffer.clear()
            }
        }
        return rows
    }

}