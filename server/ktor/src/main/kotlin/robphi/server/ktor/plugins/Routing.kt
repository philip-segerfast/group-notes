package robphi.server.ktor.plugins

import co.touchlab.kermit.Logger
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.sse.ServerSSESession
import io.ktor.server.sse.sse
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.internal.throwMissingFieldException
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import robphi.server.ktor.api.groupRoutes
import robphi.server.ktor.api.memberRoutes
import robphi.server.ktor.api.noteRoutes
import robphi.server.ktor.api.userRoutes
import robphi.server.ktor.model.UserModel
import robphi.server.ktor.repo.UserRepository
import kotlin.time.Duration.Companion.seconds

fun Application.configureRouting() {

    log.debug("Log debug!!")

    routing {

        get {
            call.respondText("Hello World!")
        }

        get("test1") {
            val user = UserModel(33, "Pelle")
//            call.respond(user)
        }

        val userRepository by inject<UserRepository>()
        userRoutes(userRepository)

        memberRoutes()

        noteRoutes()

        groupRoutes()
    }
}



fun ServerSSESession.emitFlow() {

}

/*
val priorityAsText = call.parameters["priority"]
            if (priorityAsText == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            try {
                val priority = Priority.valueOf(priorityAsText)
                val tasks = TaskRepository.tasksByPriority(priority)

                if (tasks.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respondText(
                    contentType = ContentType.parse("text/html"),
                    text = tasks.tasksAsTable()
                )
            } catch(ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest)
        }
    }
 */