package model

open class AppException(message: String) : Exception(message)

class NoMealsFoundException : AppException("No meals found!")

class EmptyListException :AppException("list is empty")

class EmptyHighCalorieListException:AppException("there is no high calories meals in list")

class WrongInputException:AppException("wrong Input")

class RichMaxAttemptException (exceptionMessage : String?):Exception(exceptionMessage)

class EmptySearchByDateListException:AppException("there is no meals in this date list")

class InvalidDateFormateException:AppException("Invalid Date Formate")

class InvalidYearException:AppException("Invalid Year Input")

class InvalidMonthException:AppException("Invalid Month Input")

class InvalidDayException:AppException("Invalid Day Input")

class InvalidIdException:AppException("Invalid ID Input")

class NoIraqiMealsFoundException : AppException("No Iraqi meals found")

class CountryMealsFetchException:AppException("Wrong country name")

