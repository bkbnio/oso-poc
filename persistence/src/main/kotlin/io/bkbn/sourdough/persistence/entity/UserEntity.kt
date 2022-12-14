package io.bkbn.sourdough.persistence.entity

import io.bkbn.sourdough.domain.User
import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.*

object UserTable : UUIDTable("users") {
  val email = varchar("email", 255).uniqueIndex()
  val createdAt = timestamp("created_at").default(Clock.System.now())
  val updatedAt = timestamp("updated_at").default(Clock.System.now())
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
  var email by UserTable.email

  companion object : UUIDEntityClass<UserEntity>(UserTable)

  fun toUser(): User = User(
    id = this.id.value,
    email = this.email
  )
}
