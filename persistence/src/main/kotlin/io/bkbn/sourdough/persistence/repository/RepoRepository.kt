package io.bkbn.sourdough.persistence.repository

import io.bkbn.sourdough.domain.Repo
import io.bkbn.sourdough.persistence.entity.RepoEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object RepoRepository {

  data class RepoCreate(
    val name: String,
    val isPublic: Boolean
  )

  fun create(
    name: String,
    isPublic: Boolean
  ): Repo = transaction {
    val entity = RepoEntity.new {
      this.name = name
      this.isPublic = isPublic
    }
    entity.toRepo()
  }

  fun read(id: UUID): Repo = transaction {
    val entity = RepoEntity.findById(id) ?: error("No repo found with id: $id")
    entity.toRepo()
  }

  fun update(
    id: UUID,
    name: String?,
    isPublic: Boolean?
  ): Repo = transaction {
    val entity = RepoEntity.findById(id) ?: error("No repo found with id: $id")
    name?.let { entity.name = it }
    isPublic?.let { entity.isPublic = it }
    entity.toRepo()
  }

  fun delete(id: UUID) = transaction {
    val entity = RepoEntity.findById(id) ?: error("No repo found with id: $id")
    entity.delete()
  }
}
