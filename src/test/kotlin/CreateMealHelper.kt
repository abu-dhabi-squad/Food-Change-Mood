import model.Food
import model.Nutrition
import java.time.LocalDate

fun createMeal(
    id: Int = 0,
    name: String? = null,
    description: String? = null,
    minutes: Int = 0,
    submittedDate: LocalDate = LocalDate.now(),
    tags: List<String> = listOf(),
    calories: Float = 0f,
    totalFat: Float = 0f,
    sugar: Float = 0f,
    sodium: Float = 0f,
    protein: Float = 0f,
    saturated: Float = 0f,
    carbohydrates: Float = 0f,
    steps: List<String> = listOf(),
    ingredients: List<String> = listOf()
): Food {
    return Food(
        id,
        name,
        minutes = minutes,
        submittedDate = submittedDate,
        tags = tags,
        nutrition = Nutrition(
            calories = calories,
            totalFat = totalFat,
            sugar,
            sodium = sodium,
            protein = protein,
            saturated = saturated,
            carbohydrates = carbohydrates
        ),
        steps = steps,
        description = description,
        ingredients = ingredients
    )
}

