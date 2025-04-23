package logic.usecase


import model.Food
import model.Nutrition

fun createFood(
    name: String?,
    description: String? = null,
    tags: List<String> = emptyList<String>(),
    ingredients: List<String> = emptyList<String>()
): Food {
    return Food(
        id = 0,
        name = name,
        minutes = 0,
        submittedDate = null,
        tags = tags,
        nutrition = Nutrition(0.0f, 0.0f, 0.0f, 0.0f, 0.0f,0.0f,0.0f), // default empty nutrition
        steps = emptyList(),
        description = description,
        ingredients = ingredients
    )
}