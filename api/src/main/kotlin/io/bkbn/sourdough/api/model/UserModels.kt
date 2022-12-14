package io.bkbn.sourdough.api.model

import io.bkbn.sourdough.api.serializer.UuidSerializer
import io.bkbn.sourdough.domain.User
import kotlinx.serialization.Serializable
import java.util.UUID

object UserModels {

  @Serializable
  data class CreateRequest(
    val email: String,
  )

  @Serializable
  data class UpdateRequest(
    val email: String? = null,
  )

  @Serializable
  data class Response(
    @Serializable(with = UuidSerializer::class)
    val id: UUID,
    val email: String,
  ) {
    companion object {
      fun fromUser(u: User): Response = Response(
        u.id,
        u.email,
      )
    }
  }
}
