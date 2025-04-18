package model

open class AppException(message: String) : Exception(message)

class NoMealsFoundException : AppException("No meals found!")


class EmptyHighCalorieListException:AppException("there is no high calories meals in list")

class WrongInputException:AppException("wrong Input")

class RichMaxAttemptException (exceptionMessage : String?):Exception(exceptionMessage)

class NoKetoMealFoundException : Exception("No keto meal found!")



class EmptyListException:AppException("There is no item in list match the input")
class NoIraqiMealsFoundException : AppException("No Iraqi meals found")

class CountryMealsFetchException:AppException("Wrong country name")