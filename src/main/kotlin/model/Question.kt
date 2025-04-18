package model

data class IngredientQuestion(
    val mealName: String,
    val correctAnswer : String,
    private val answers: List<String>,
){
    fun getAnswers() = (answers + correctAnswer).shuffled()
}