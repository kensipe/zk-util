#!/usr/bin/env amm

import ammonite.ops._
import ammonite.ops.ImplicitWd._

import $file.util

import com.twitter.conversions.time._
import com.twitter.zk._
import com.twitter.util.{Duration, JavaTimer, Await}


implicit val javaTimer = new JavaTimer(true)

@main
def set(path: String, value: String=""): Unit = {
  setDataValue(path, value.getBytes())
  get(path)
}

@main
def setId(path: String, id: String=""): Unit = {
  // // data for framework ID is protobuf which has some delimiters
  val head: Array[Byte] = Array(0x0a, ')')
  setDataValue(path, head ++ id.getBytes())
  get(path)
}

@main
def get(path: String): Unit = {
  val value = dataValue(path)
  println(s"value: ${value} in path: ${path}")
}

def getZNode(path: String): ZNode = {
  val zk = ZkClient.apply("localhost:2181", 5 seconds)
  ZNode(zk, path)
}

def dataValue(path: String): String = {
  val zdata = Await.result(getZNode(path).getData())
  new String(zdata.bytes)
}

def setDataValue(path: String, value: Array[Byte]): Unit = {
  val znode = getZNode(path)
  val zdata = Await.result(znode.getData())
  val dversion = zdata.stat.getVersion()

  znode.setData(value, dversion)
}
