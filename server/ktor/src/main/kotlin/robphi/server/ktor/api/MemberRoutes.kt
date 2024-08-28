package robphi.server.ktor.api

import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import robphi.server.ktor.repo.UserRepository

fun Route.memberRoutes() {
    route("/user") {

    }
}