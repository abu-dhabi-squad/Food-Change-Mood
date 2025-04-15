package logic.repository

import model.Food

interface FoodRepository {
    fun getFoods() : Result<List<Food>>
}