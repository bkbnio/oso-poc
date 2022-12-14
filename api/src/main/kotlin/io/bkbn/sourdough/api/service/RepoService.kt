package io.bkbn.sourdough.api.service

import io.bkbn.sourdough.api.model.RepoModels
import io.bkbn.sourdough.persistence.repository.RepoRepository
import java.util.*

object RepoService {

  fun create(request: RepoModels.CreateRequest): RepoModels.Response {
    val result = RepoRepository.create(
      name = request.name,
      isPublic = request.isPublic,
    )
    return RepoModels.Response.fromRepo(result)
  }

  fun read(id: UUID): RepoModels.Response {
    val result = RepoRepository.read(id)
    return RepoModels.Response.fromRepo(result)
  }

  fun update(id: UUID, request: RepoModels.UpdateRequest): RepoModels.Response {
    val result = RepoRepository.update(
      id = id,
      name = request.name,
      isPublic = request.isPublic,
    )
    return RepoModels.Response.fromRepo(result)
  }

  fun delete(id: UUID) {
    RepoRepository.delete(id)
  }
}
