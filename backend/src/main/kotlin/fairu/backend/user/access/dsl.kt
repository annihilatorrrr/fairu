package fairu.backend.user.access

import fairu.backend.user.User
import fairu.backend.user.UserPrincipal
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// TODO: Find way to make ApplicationCall#principal return null in-case of missing scope.
// TODO: Improve communication of missing scopes.

fun Route.scopedAccess(
    vararg scopes: AccessScope,
    optional: Boolean = false,
    block: Route.() -> Unit
) = authenticate("session", "access_token", optional = optional) {
    intercept(ApplicationCallPipeline.Call) {

        val principal = call.principal<UserPrincipal>()
        when {
            principal == null -> if (!optional) {
                call.respond(HttpStatusCode.FailedDependency)
            }

            !scopes.all { principal.canAccess(it) } -> if (!optional) {
                call.respond(HttpStatusCode.Forbidden)
            } else {
            }
        }
    }

    block()
}

val ApplicationCall.authenticatedUser: User?
    get() = principal<UserPrincipal>()?.user

