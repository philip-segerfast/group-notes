package robphi.server.ktor.api

import co.touchlab.kermit.Logger
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import robphi.server.ktor.model.UserModel
import robphi.server.ktor.repo.UserRepository

fun Route.userRoutes(userRepository: UserRepository) {
    route("/user") {
        post("/create/{name}") {
            val logger = Logger.withTag("user/create/{name}")
            logger.d("Parameters: ${ call.parameters.entries().joinToString { "${it.key}: ${it.value}" } }")

            val name = call.parameters["name"] ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            logger.d("Name: $name")

            val user = kotlin.runCatching {
                userRepository.createUser(name)
            }.getOrElse {
                error("Failed to create user: ${it.stackTraceToString()}")
            }

            logger.d("User to be returned: $user")

            call.respond(user)
        }

        patch("/update/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@patch
            }

            val randomNewUser = listOf("Kevin", "Robin", "Philip", "Kalle", "Stefan", "Johan")
                .random().let { UserModel(id, it) }

            userRepository.updateUser(randomNewUser)

            call.respond(randomNewUser)
        }

        get("/getUsers") {
            call.respond(userRepository.getUsers())
        }

        get("/getUser/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: run {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(userRepository.getUser(id))
        }

    }
}
