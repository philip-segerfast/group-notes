package robphi.server.ktor

import SERVER_PORT
import co.touchlab.kermit.Logger
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.sse.SSE
import robphi.server.ktor.koin.Koin
import robphi.server.ktor.di.appModule
import robphi.server.ktor.di.databaseModule
import robphi.server.ktor.plugins.configureRouting
import robphi.server.ktor.plugins.configureSerialization
import robphi.server.ktor.repo.userModule


fun main() {
    Logger.d("Invoking embedded server")
    embeddedServer(
        factory = Netty,
        port = SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

private fun Application.module() {
    install(Koin) {
        modules(databaseModule, userModule, appModule)
    }
    install(SSE)
    configureSerialization()
    configureRouting()
}
