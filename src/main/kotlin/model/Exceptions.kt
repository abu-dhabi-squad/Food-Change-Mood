package model

open class AppException(message: String) : Exception(message)

class WrongInputException : AppException("wrong Input")

class NoEasyMealsFoundException : AppException("No easy meals found matching the criteria.")

class NoMealsFoundException : AppException("No meals found!")

class EmptyListException : AppException("list is empty")

class EmptyHighCalorieListException : AppException("there is no high calories meals in list")

class RichMaxAttemptException(preparationTime:Int) : Exception("You have reached the maximum number of attempts.The correct preparation time is $preparationTime minutes")

class EmptyHealthFoodListListException : AppException("No healthy meals found")

class EmptySearchByDateListException : AppException("there is no meals in this date list")

class InvalidDateFormatException : AppException("Invalid Date Format")

class InvalidYearException : AppException("Invalid Year Input")

class InvalidDateException : AppException("Invalid Date Input")

class InvalidIdException : AppException("Invalid ID Input")

class NoIraqiMealsFoundException : AppException("No Iraqi meals found")

class CountryMealsFetchException : AppException("Wrong country name")