package io.bkbn.sourdough.persistence.repository

import io.bkbn.sourdough.domain.User
import io.bkbn.sourdough.persistence.entity.UserEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object UserRepository {

  data class UserCreate(
    val email: String
  )

  fun create(
    email: String
  ): User = transaction {
    val entity = UserEntity.new {
      this.email = email
    }
    entity.toUser()
  }

  fun read(id: UUID): User = transaction {
    val entity = UserEntity.findById(id) ?: error("No user found with id: $id")
    entity.toUser()
  }

  fun update(
    id: UUID,
    email: String?
  ): User = transaction {
    val entity = UserEntity.findById(id) ?: error("No user found with id: $id")
    email?.let { entity.email = it }
    entity.toUser()
  }

  fun delete(id: UUID) = transaction {
    val entity = UserEntity.findById(id) ?: error("No user found with id: $id")
    entity.delete()
  }
}
