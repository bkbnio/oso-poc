package io.bkbn.sourdough.persistence.entity

import io.bkbn.sourdough.domain.Repo
import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.*

object RepoTable : UUIDTable("repos") {
  val name = varchar("name", 255).uniqueIndex()
  val isPublic = bool("is_public")
  val createdAt = timestamp("created_at").default(Clock.System.now())
  val updatedAt = timestamp("updated_at").default(Clock.System.now())
}

class RepoEntity(id: EntityID<UUID>) : UUIDEntity(id) {
  var name by RepoTable.name
  var isPublic by RepoTable.isPublic

  companion object : UUIDEntityClass<RepoEntity>(RepoTable)

  fun toRepo(): Repo = Repo(
    id = this.id.value,
    name = this.name,
    isPublic = this.isPublic
  )
}
