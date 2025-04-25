package model

data class IngredientQuestion(
    val mealId: Int,
    val mealName: String,
    val correctAnswer : String,
    private val answers: List<String>,
){
    fun getAnswers() = (answers + correctAnswer).shuffled()
}