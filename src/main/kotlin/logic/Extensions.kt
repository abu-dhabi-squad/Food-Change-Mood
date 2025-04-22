package logic

import kotlin.math.min

fun <T> List<T>.takeShuffled(count: Int): List<T> {
    if (count < 1)
        throw IllegalArgumentException("List is out of range")

    return indices.shuffled().take(min(count, size)).map { this[it] }
}