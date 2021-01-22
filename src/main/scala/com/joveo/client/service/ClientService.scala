package com.joveo.client.service

import com.joveo.client.Client
import com.joveo.client.repo.Mongo
import org.mongodb.scala.Completed
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.FindOneAndUpdateOptions
import spray.json.enrichAny
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Updates.{combine, set}

import scala.concurrent.Future

class ClientService {
  val DB:Mongo = new Mongo()
  def getClient(id:String):Future[Option[Client]]= {
    DB.clientCollection.find(equal("id",id)).headOption()
  }
  def saveClient(c:Client):Future[Completed]={
    DB.clientCollection.insertOne(c).toFuture()
  }
  def updateClient(id:String,c:Client):Future[Client]={
    DB.clientCollection.findOneAndUpdate(equal("id",id),setBsonValue(c)).toFuture()
  }
  def getClients(off:Int,limit:Int):Future[Seq[Client]]={
    DB.clientCollection.find().skip(off).limit(limit).toFuture()
  }
  private def setBsonValue(c:Client): Bson = {
    combine(
      set("id", c.id),
      set("name",c.name),
      set("inboundFeedUrl",c.inboundFeedUrl),
    )
  }
}
