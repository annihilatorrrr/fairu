package fairu.backend.routes.users

import fairu.backend.user.User
import fairu.backend.utils.ext.respond
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

val PipelineContext<Unit, ApplicationCall>.logPrefix
    get() = "User[${call.user.username}: ${call.user.id}]"

@Serializable
data class GetUsersResponse(@SerialName("total_users") val totalUsers: Long)

fun Route.users() = route("/users") {
    // GET /
    get {
        val users = User.collection.countDocuments()
        respond(GetUsersResponse(users))
    }

    // PUT /
    create()

    // GET /:user_id
    user()
}
