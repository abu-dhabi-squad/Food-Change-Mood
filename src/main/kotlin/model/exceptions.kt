package model

open class AppException(message: String): Exception(message)

class EmptyListException:AppException("there is no item in list")

class EmptyHighCalorieListException:AppException("there is no high calories meals in list")

class EmptySearchByDateListException:AppException("there is no meals in this date list")

class WrongInputException:AppException("wrong Input")