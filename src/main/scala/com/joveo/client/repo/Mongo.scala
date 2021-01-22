package com.joveo.client.repo

import com.joveo.client.Client
import org.bson.codecs.configuration.CodecRegistries._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

class Mongo{
  val clientCodecProvider= Macros.createCodecProvider[Client]()
  val codecRegistry = fromRegistries(fromProviders(clientCodecProvider),DEFAULT_CODEC_REGISTRY)
  val DB:MongoDatabase = MongoClient().getDatabase("Joveo").withCodecRegistry(codecRegistry)
  val clientCollection:MongoCollection[Client] = DB.getCollection("Clients")
}
