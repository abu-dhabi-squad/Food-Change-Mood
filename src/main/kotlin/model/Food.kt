package model

import java.time.LocalDate

data class Food(
    val id: Int,
    val name: String?,
    val minutes: Int,
    val submittedDate: LocalDate?,
    val tags: List<String>,
    val nutrition: Nutrition,
    val steps: List<String>,
    val description : String?,
    val ingredients: List<String>
)
{
    fun showDetails(){
        println("meal ID : " + this.id)
        this.name?.also {name -> println("Meal Name : "+ name) }
        println("Meal Description : "+ this.description+"\n")
        println("preparation time : " + this.minutes)
        this.submittedDate?.also {date ->
            println("submittedDate : " +date.dayOfMonth+"-"+date.month?.value+"-"+date.year+"\n")
        }
        println("Tags : "+this.tags)
        println("\n"+"nutrition : ")
        this.nutrition.showDetails()

        println("\n"+"ingredients : ")
        this.ingredients.forEach { println("- " +it) }
        println("\n"+"steps : ")
        this.steps.forEach { println("- " + it) }

    }
}


