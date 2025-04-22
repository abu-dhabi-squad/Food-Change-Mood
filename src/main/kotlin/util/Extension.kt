package util

import model.Food
import kotlin.math.min

fun Food.print() {
    println("\n----- Meal -----")
    println("Name: $name")
    println("Minutes: $minutes")
    println("SubmittedDate: $submittedDate")
    println("Description: $description")
    println("Tags: $tags")
    println("Ingredients: $ingredients")
    println("Steps: $steps")
    println("Nutrition:")
    nutrition.print("\t- ")
}

fun Food.showDetails() {
    println("\n" + "meal ID : " + this.id)
    this.name?.also { name -> println("Meal Name : " + name) }
    println("Meal Description : " + this.description + "\n")
    println("preparation time : " + this.minutes)
    this.submittedDate?.also { date ->
        println("submittedDate : " + date.dayOfMonth + "-" + date.month?.value + "-" + date.year + "\n")
    }
    println("Tags : " + this.tags)
    println("\n" + "nutrition : ")
    this.nutrition.showDetails()

    println("\n" + "ingredients : ")
    this.ingredients.forEach { println("- " + it) }
    println("\n" + "steps : ")
    this.steps.forEach { println("- " + it) }

}

fun <T> List<T>.takeShuffled(count: Int): List<T> {
    if (count < 1)
        throw IllegalArgumentException("List is out of range")

    return indices.shuffled().take(min(count, size)).map { this[it] }
}