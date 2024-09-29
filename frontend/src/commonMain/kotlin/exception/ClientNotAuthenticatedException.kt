package exception

class ClientNotAuthenticatedException(message: String = "Client not signed in") : Exception(message)
