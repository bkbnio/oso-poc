package io.bkbn.sourdough.persistence.entity

import io.bkbn.sourdough.domain.User
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object UserTable : UUIDTable("user") {
  val email = varchar("name", 255).uniqueIndex()
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
  var email by UserTable.email

  companion object : UUIDEntityClass<UserEntity>(UserTable)

  fun toUser(): User = User(
    id = this.id.value,
    email = this.email
  )
}
