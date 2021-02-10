package io.scalac.lab.api.model

import io.circe.Codec
import io.circe.generic.AutoDerivation
import io.circe.generic.semiauto.deriveCodec
import sttp.tapir.Schema
import sttp.tapir.generic.SchemaDerivation

sealed trait ApiError extends Product with AutoDerivation with SchemaDerivation {
  self =>

  val code = self match {
    case ApiError.UnauthorizedError(_) => 401
    case ApiError.NotFoundError(_) => 404
    case ApiError.BadRequestError(_) => 400
  }

}

object ApiError {
  implicit val schema: Schema[ApiError] = Schema.derived

  implicit val codec: Codec[ApiError] = deriveCodec // this is for circe, you'll need Format thing for akka

  case class UnauthorizedError(reason: String) extends ApiError

  case class NotFoundError(message: String) extends ApiError

  case class BadRequestError(reason: String) extends ApiError

}
