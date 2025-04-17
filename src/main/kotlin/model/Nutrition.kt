package model

data class Nutrition(
    val calories: Float,
    val totalFat: Float,
    val sugar: Float,
    val sodium: Float,
    val protein: Float,
    val saturated: Float,
    val carbohydrates: Float
)
{
    fun showDetails(){
        this.also {
            println("- calories = "+ it.calories)
            println("- sodium = "+ it.sodium)
            println("- sugar = "+ it.sugar)
            println("- protein = "+ it.protein)
            println("- totalFat = "+ it.totalFat)
            println("- carbohydrates = "+ it.carbohydrates)
            println("- saturated = "+ it.saturated)
        }
    }
}