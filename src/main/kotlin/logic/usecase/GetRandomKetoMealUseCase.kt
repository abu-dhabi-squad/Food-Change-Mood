package logic.usecase

import model.Food
import model.NoKetoMealFoundException

class GetRandomKetoMealUseCase {

    operator fun invoke(ketoMeals:List<Food> , shownKetoMealsId:List<Int> ): Food {
        if(ketoMeals.isEmpty())
            throw NoKetoMealFoundException()
        return ketoMeals.filter { it.id !in shownKetoMealsId }.random()
    }
}