package model

open class AppException(message: String): Exception(message)

class NoMealsFoundException: AppException("No meals found!")
