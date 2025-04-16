package model

class NoMealFoundException (exceptionMessage : String?):Exception(exceptionMessage)

open class GuessFoodPreparationTimeException (exceptionMessage : String?):Exception(exceptionMessage)

class RichMaxAttemptException (exceptionMessage : String?):GuessFoodPreparationTimeException(exceptionMessage)

class GuessWrongPreparationTimeException (exceptionMessage : String?):GuessFoodPreparationTimeException(exceptionMessage)

class InValidPreparationTimeException (exceptionMessage : String?):GuessFoodPreparationTimeException(exceptionMessage)
