package data

import logic.repository.FoodRepository
import model.Food
import model.Nutrition
import java.time.LocalDate

class FakeCsvFoodRepositoryImp() : FoodRepository {
    private val mexicanStyle = Food(
        id = 137739,
        name = "arriba baked winter squash mexican style",
        minutes = 55,
        submittedDate = LocalDate.of(2005, 9, 16),
        tags = listOf(
            "60-minutes-or-less",
            "time-to-make",
            "course",
            "main-ingredient",
            "cuisine",
            "preparation",
            "occasion",
            "north-american",
            "side-dishes",
            "vegetables",
            "mexican",
            "easy",
            "fall",
            "holiday-event",
            "vegetarian",
            "winter",
            "dietary",
            "christmas",
            "seasonal",
            "squash"
        ),
        nutrition = Nutrition(
            calories = 51.5f,
            totalFat = 0.0f,
            sugar = 13.0f,
            sodium = 0.0f,
            protein = 2.0f,
            saturated = 0.0f,
            carbohydrates = 4.0f
        ),
        steps = listOf(
            "make a choice and proceed with recipe",
            "depending on size of squash , cut into half or fourths",
            "remove seeds",
            "for spicy squash , drizzle olive oil or melted butter over each cut squash piece",
            "season with mexican seasoning mix ii",
            "for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece",
            "season with sweet mexican spice mix",
            "bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin",
            "be careful not to burn the squash especially if you opt to use sugar or butter",
            "if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking",
            "if desired , season with salt"
        ),
        description = "autumn is my favorite time of year to cook! this recipe can be prepared either spicy or sweet, your choice! two of my posted mexican-inspired seasoning mix recipes are offered as suggestions.",
        ingredients = listOf(
            "winter squash",
            "mexican seasoning",
            "mixed spice",
            "honey",
            "butter",
            "olive oil",
            "salt"
        )
    )

    private val mexicanChili = Food(
        id = 112144,
        name = "mexican cowboy chili con carne",
        minutes = 240,
        submittedDate = LocalDate.of(2011, 9, 16),
        tags = listOf(
            "main-dish",
            "mexican",
            "spicy",
            "slow-cooker",
            "comfort-food",
            "gluten-free"
        ),
        nutrition = Nutrition(
            calories = 390.0f,
            totalFat = 20.0f,
            sugar = 10.0f,
            sodium = 570.0f,
            protein = 34.0f,
            saturated = 6.5f,
            carbohydrates = 22.0f
        ),
        steps = listOf(
            "brown beef in skillet with garlic and onion",
            "transfer to crockpot and add tomatoes, beans, and spices",
            "cook on low for 6 hours or high for 3",
            "stir in fresh cilantro before serving",
            "serve with warm tortillas or cornbread"
        ),
        description = "a classic mexican 🇲🇽 chili with rich, smoky depth and just the right kick. made for slow weekends or feeding a hungry crowd. bold, beefy, and unforgettable.",
        ingredients = listOf(
            "ground beef",
            "onion",
            "garlic",
            "crushed tomatoes",
            "kidney beans",
            "black beans",
            "chili powder",
            "cumin",
            "paprika",
            "salt",
            "cilantro"
        )
    )

    private val italianMeatballs = Food(
        id = 112145,
        name = "italian sunday meatballs & marinara",
        minutes = 180,
        submittedDate = LocalDate.of(2014, 5, 4),
        tags = listOf(
            "italian",
            "main-dish",
            "slow-cooker",
            "comfort-food",
            "meat",
            "sauce",
            "classic"
        ),
        nutrition = Nutrition(
            calories = 440.0f,
            totalFat = 25.0f,
            sugar = 6.0f,
            sodium = 650.0f,
            protein = 32.0f,
            saturated = 9.0f,
            carbohydrates = 20.0f
        ),
        steps = listOf(
            "mix beef, breadcrumbs, parmesan, egg, and herbs",
            "form into meatballs and sear in skillet",
            "transfer to crockpot and cover with marinara sauce",
            "slow cook on low for 4-5 hours",
            "serve over pasta or in toasted hoagie rolls"
        ),
        description = "straight outta nonna’s 🇮🇹 kitchen. this slow-cooked italian classic fills your home with aromas of garlic, basil, and tomato. perfect for sundays or anytime you're feeling nostalgic.",
        ingredients = listOf(
            "ground beef",
            "breadcrumbs",
            "parmesan cheese",
            "egg",
            "garlic",
            "italian seasoning",
            "marinara sauce",
            "olive oil",
            "salt",
            "pepper"
        )
    )

    private val thaiCurry = Food(
        id = 112146,
        name = "thai coconut chicken curry",
        minutes = 150,
        submittedDate = LocalDate.of(2020, 8, 8),
        tags = listOf(
            "thai",
            "curry",
            "slow-cooker",
            "asian",
            "spicy",
            "gluten-free",
            "dairy-free"
        ),
        nutrition = Nutrition(
            calories = 360.0f,
            totalFat = 18.0f,
            sugar = 5.0f,
            sodium = 520.0f,
            protein = 30.0f,
            saturated = 12.0f,
            carbohydrates = 20.0f
        ),
        steps = listOf(
            "place chicken, curry paste, and chopped veggies in slow cooker",
            "pour in coconut milk and stir",
            "cook on low for 6 hours or high for 3",
            "add fresh basil and a squeeze of lime before serving",
            "serve over jasmine rice"
        ),
        description = "a rich, fragrant curry that brings the flavors of thailand 🇹🇭 right to your kitchen. creamy coconut milk, red curry heat, and tender chicken — it’s a warm-weather hug in a bowl.",
        ingredients = listOf(
            "chicken breast",
            "red curry paste",
            "coconut milk",
            "bell peppers",
            "carrots",
            "onion",
            "basil",
            "lime",
            "fish sauce",
            "ginger",
            "jasmine rice"
        )
    )


    private val breakfast = Food(
        id = 31490,
        name = "arriba baked winter squash mexican style",
        minutes = 30,
        submittedDate = LocalDate.of(2002, 6, 17),
        tags = listOf(
            "30-minutes-or-less",
            "time-to-make",
            "course",
            "main-ingredient",
            "cuisine",
            "preparation",
            "occasion",
            "north-american",
            "breakfast",
            "main-dish",
            "pork",
            "american",
            "oven",
            "easy",
            "kid-friendly",
            "pizza",
            "dietary",
            "northeastern-united-states",
            "meat",
            "equipment"
        ),
        nutrition = Nutrition(
            calories = 173.4f,
            totalFat = 18.0f,
            sugar = 0.0f,
            sodium = 17.0f,
            protein = 22.0f,
            saturated = 35.0f,
            carbohydrates = 1.0f
        ),
        steps = listOf(
            "preheat oven to 425 degrees f",
            "press dough into the bottom and sides of a 12 inch pizza pan",
            "bake for 5 minutes until set but not browned",
            "cut sausage into small pieces",
            "whisk eggs and milk in a bowl until frothy",
            "spoon sausage over baked crust and sprinkle with cheese",
            "pour egg mixture slowly over sausage and cheese",
            "s& p to taste",
            "bake 15-20 minutes or until eggs are set and crust is brown"
        ),
        description = "this recipe calls for the crust to be prebaked a bit before adding ingredients. feel free to change sausage to ham or bacon. this warms well in the microwave for those late risers.",
        ingredients = listOf(
            "prepared pizza crust",
            "sausage patty",
            "eggs",
            "milk",
            "salt and pepper",
            "cheese"
        )
    )

    private val crockpoiltChail = Food(
        id = 112140,
        name = "arriba baked winter squash mexican style",
        minutes = 130,
        submittedDate = LocalDate.of(2005, 2, 25),
        tags = listOf(
            "time-to-make",
            "course",
            "preparation",
            "main-dish",
            "chili",
            "crock-pot-slow-cooker",
            "dietary",
            "equipment",
            "4-hours-or-less"
        ),
        nutrition = Nutrition(
            calories = 269.8f,
            totalFat = 22.0f,
            sugar = 32.0f,
            sodium = 48.0f,
            protein = 39.0f,
            saturated = 27.0f,
            carbohydrates = 5.0f
        ),
        steps = listOf(
            "brown ground beef in large pot",
            "add chopped onions to ground beef when almost brown and sautee until wilted",
            "add all other ingredients",
            "add kidney beans if you like beans in your chili",
            "cook in slow cooker on high for 2-3 hours or 6-8 hours on low",
            "serve with cold clean lettuce and shredded cheese"
        ),
        description = "this modified version of 'mom's' chili was a hit at our 2004 christmas party. we made an extra large pot to have some left to freeze but it never made it to the freezer. it was a favorite by all. perfect for any cold and rainy day. you won't find this one in a cookbook. it is truly an original.",
        ingredients = listOf(
            "ground beef",
            "yellow onions",
            "diced tomatoes",
            "tomato paste",
            "tomato soup",
            "rotel tomatoes",
            "kidney beans",
            "water",
            "chili powder",
            "ground cumin",
            "salt",
            "lettuce",
            "cheddar cheese"
        )
    )

    override fun getFoods(): Result<List<Food>> =
        Result.success(
            listOf(
                mexicanStyle,
                mexicanChili,
                italianMeatballs,
                thaiCurry,
                breakfast,
                crockpoiltChail
            )
        )

}