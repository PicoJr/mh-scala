package util

import scala.util.Random

/**
  * Created by nol on 17/11/17.
  */
object Procedural {

  def pickRandomFromSeq[T](seq: Seq[T]): Option[T] = {
    seq.lift(Random.nextInt(seq.size))
  }

  def pickRandom[T](e: T*): Option[T] = {
    pickRandomFromSeq(e.toSeq)
  }

  private def getVariation(percentage: Int): Double = Random.nextDouble() * percentage / 100.0

  def getRandomValue(level: Int, base: Int, growth: Double, percentage: Int): Int = {
    val value: Double = base * Math.pow(growth, level)
    (value + (getVariation(percentage) * value)).toInt
  }

}
