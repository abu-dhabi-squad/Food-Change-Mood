package model

open class AppException(message: String): Exception(message)


class EmptyHighCalorieListException:AppException("there is no high calories meals in list")

class WrongInputException:AppException("wrong Input")

class EmptyListException:AppException("There is no item in list match the input")

class CountryMealsFetchException:AppException("Wrong country name")