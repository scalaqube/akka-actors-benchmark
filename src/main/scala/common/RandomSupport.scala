package common

import scala.util.Random

trait RandomSupport extends App {

  def randomKey(max: Int) = Random.nextInt(max).toString
  def randomValue() = Random.alphanumeric.take(12).mkString
}
