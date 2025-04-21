package data

import model.Food

interface FoodParser {
    fun parse(): List<Food>
}