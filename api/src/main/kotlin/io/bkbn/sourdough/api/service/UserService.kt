package io.bkbn.sourdough.api.service

import io.bkbn.sourdough.api.model.UserModels
import io.bkbn.sourdough.persistence.repository.UserRepository
import java.util.*

object UserService {

  fun create(request: UserModels.CreateRequest): UserModels.Response {
    val result = UserRepository.create(
      email = request.email,
    )
    return UserModels.Response.fromUser(result)
  }


  fun read(id: UUID): UserModels.Response {
    val result = UserRepository.read(id)
    return UserModels.Response.fromUser(result)
  }

  fun update(id: UUID, request: UserModels.UpdateRequest): UserModels.Response {
    val result = UserRepository.update(
      id = id,
      email = request.email,
    )
    return UserModels.Response.fromUser(result)
  }

  fun delete(id: UUID) {
    UserRepository.delete(id)
  }
}
