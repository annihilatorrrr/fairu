package fairu.backend.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Config(val fairu: Fairu) {
    @Serializable
    data class Fairu(
        val management: Management = Management(),
        val frontend: Frontend = Frontend(),
        val server: Server = Server(),
        val s3: S3,
        val jwt: Jwt,
        val db: Database,
    ) {
        @Serializable
        data class Management(
            /** Usernames allowed to be used in POST /v1/users */
            @SerialName("allowed-usernames")
            val allowedUsernames: List<String>? = null,
        )

        @Serializable
        data class Frontend(
            /** Whether frontend-specific routes should be added. */
            val enabled: Boolean = false,
            val url: String? = null
        )

        @Serializable
        data class Server(
            val host: String = "0.0.0.0",
            val port: Long = 3232 // we use a long because Ktoml is shit
        )

        @Serializable
        data class Database(
            val uri: String,
            @SerialName("database-name")
            val databaseName: String
        )

        @Serializable
        data class S3(
            val endpoint: String,
            val bucket: String,
            @SerialName("access-key")
            val accessKey: String,
            @SerialName("secret-key")
            val secretKey: String,
        )

        @Serializable
        data class Jwt(
            val secret: String
        )
    }
}
