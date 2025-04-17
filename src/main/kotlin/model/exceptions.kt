package model


open class AppException(message: String): Exception(message)

class EmptyListException:AppException("There is no item in list match the input")

class WrongInputException:AppException("Wrong country name")