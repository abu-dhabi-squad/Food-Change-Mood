package model


open class AppException(message: String): Exception(message)

class EmptyListException:AppException("There is no item in list match the input")
class CountryMealsFetchException:AppException("Wrong country name")