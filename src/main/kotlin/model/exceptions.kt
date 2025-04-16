package model

open class ValidationException(message: String): Exception(message)

class EmptyListException:ValidationException("there is no item in list")


