package com.joveo.client.api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.joveo.client.Client
import com.joveo.client.repo.Mongo
import com.joveo.client.service.ClientService
import org.mongodb.scala.Completed
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.FindOneAndUpdateOptions
import org.mongodb.scala.model.Updates.{combine, set}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}
class ClientApi {
  import org.json4s.DefaultFormats
  import org.json4s.native.Serialization
  import de.heikoseeberger.akkahttpjson4s.Json4sSupport._

  implicit val serialization = Serialization
  implicit val formats = DefaultFormats
  val clientService = new ClientService

  def getClient(id:String):Future[Option[Client]]= {
    clientService.getClient(id)
  }
  def saveClient(c:Client):Future[Completed]={
    clientService.saveClient(c)
  }
  def updateClient(id:String,c:Client):Future[Client]={
    clientService.updateClient(id,c)
  }
  def getClients(off:Int,limit:Int):Future[Seq[Client]]={
    clientService.getClients(off,limit)
  }
  def routes: Route = concat(
    pathPrefix("client"){
      get{
        path(Segment){
          id =>
            val maybeClient:Future[Option[Client]] = getClient(id)
            onComplete(maybeClient){
              case Success(Some(client)) => complete(client)
              case Success(None) => complete(StatusCodes.OK, "Nothing found in the client DB")
              case Failure(exception) =>{
                complete(StatusCodes.NotFound)
              }
            }
        }
      }~
        post{
          path(Segment/"update"){
            id=>
              entity(as[Client]){
                client =>
                  val updated = updateClient(id,client)
                  onSuccess(updated){
                    _=>complete(s"client $client.name updated")
                  }
              }
          }~
            entity(as[Client]){
              client=>
                val saved = saveClient(client)
                onSuccess(saved){
                  _=>complete("client saved!")
                }
            }
        }
    },
    pathPrefix("clients"){
      parameters("off".as[Int],"limit".as[Int]) {
        (off, limit) => {
          get {
            onComplete(getClients(off, limit)) {
              case Success(list) => complete(list)
              case Failure(exception) => {
                complete(StatusCodes.NotFound)
              }
            }
          }
        }
      }
    }
  )
}