package model

class NoMealFoundException (exceptionMessage : String?):Exception(exceptionMessage)

open class GuessFoodPreparationTimeException (exceptionMessage : String?):Exception(exceptionMessage)

class RichMaxAttemptException (exceptionMessage : String?):GuessFoodPreparationTimeException(exceptionMessage)

class GuessWrongPreparationTimeException (exceptionMessage : String?):GuessFoodPreparationTimeException(exceptionMessage)

class InValidPreparationTimeException (exceptionMessage : String?):GuessFoodPreparationTimeException(exceptionMessage)
open class AppException(message: String): Exception(message)

class EmptyListException:AppException("there is no item in list")

class EmptyHighCalorieListException:AppException("there is no high calories meals in list")

class WrongInputException:AppException("wrong Input")

