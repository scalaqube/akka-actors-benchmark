package simplemap

import common.RandomSupport

import scala.io.StdIn.readLine

object SimpleMapApp extends RandomSupport {

  private val Attempts = 2500000
  private val KeySize  = 5000000

  private def populate() = {
    val start = System.currentTimeMillis()
    val result = ((1 to Attempts) map (i => randomKey(KeySize) -> randomValue())).toMap
    val end = System.currentTimeMillis()
    println(s" Populating done in ${end - start} ms.")
    result
  }

  private def query(map: Map[String, String]) = {
    val start = System.currentTimeMillis()
    (1 to KeySize) foreach (_ => map.get(randomKey(KeySize)))
    val end = System.currentTimeMillis()
    println(s" Querying done in ${end - start} ms.")
  }

  readLine("Press enter to start the benchmark...")
  val map = populate()
  query(map)
  readLine("Press enter to exit...")
  println(map.get(""))
}