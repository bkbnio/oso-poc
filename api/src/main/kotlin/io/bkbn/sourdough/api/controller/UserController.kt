package io.bkbn.sourdough.api.controller

import io.bkbn.kompendium.core.metadata.DeleteInfo
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.metadata.PutInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.bkbn.sourdough.api.model.UserModels
import io.bkbn.sourdough.api.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

object UserController {

  fun Route.userHandler() {
    route("/user") {
      rootDocumentation()
      post {
        val request = call.receive<UserModels.CreateRequest>()
        val result = UserService.create(request)
        call.respond(HttpStatusCode.Created, result)
      }
      route("/{id}") {
        idDocumentation()
        get {
          val id = UUID.fromString(call.parameters["id"])
          val result = UserService.read(id)
          call.respond(HttpStatusCode.OK, result)
        }
        put {
          val id = UUID.fromString(call.parameters["id"])
          val request = call.receive<UserModels.UpdateRequest>()
          val result = UserService.update(id, request)
          call.respond(HttpStatusCode.Created, result)
        }
        delete {
          val id = UUID.fromString(call.parameters["id"])
          UserService.delete(id)
          call.respond(HttpStatusCode.NoContent)
        }
      }
    }
  }

  private fun Route.rootDocumentation() {
    install(NotarizedRoute()) {
      tags = setOf("User")
      post = PostInfo.builder {
        summary("Create User")
        description("Create a new user")
        request {
          requestType<UserModels.CreateRequest>()
          description("The user to create")
        }
        response {
          responseCode(HttpStatusCode.Created)
          responseType<UserModels.Response>()
          description("The created user")
        }
      }
    }
  }

  private fun Route.idDocumentation() {
    install(NotarizedRoute()) {
      tags = setOf("User")
      get = GetInfo.builder {
        summary("Read User")
        description("Read a user")
        response {
          responseCode(HttpStatusCode.OK)
          responseType<UserModels.Response>()
          description("The user")
        }
      }
      put = PutInfo.builder {
        summary("Update User")
        description("Update a user")
        request {
          requestType<UserModels.UpdateRequest>()
          description("The user to update")
        }
        response {
          responseCode(HttpStatusCode.Created)
          responseType<UserModels.Response>()
          description("The updated user")
        }
      }
      delete = DeleteInfo.builder {
        summary("Delete User")
        description("Delete a user")
        response {
          responseCode(HttpStatusCode.NoContent)
          description("The user was deleted")
        }
      }
    }
  }
}
