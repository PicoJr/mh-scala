package game.util

import scala.util.Random

/**
  * Created by nol on 17/11/17.
  */
object Procedural {

  /**
    *
    * @param seq elements to choose from
    * @tparam T any
    * @return Some(random element from seq) if seq non empty else None
    */
  def pickRandomFromSeq[T](seq: Seq[T]): Option[T] = {
    seq.lift(Random.nextInt(seq.size))
  }

  /**
    *
    * @param elements to choose from
    * @tparam T any
    * @return Some(random element from seq) if seq non empty else None
    */
  def pickRandom[T](elements: T*): Option[T] = {
    pickRandomFromSeq(elements.toSeq)
  }

  private def getVariation(percentage: Int): Double = Random.nextDouble() * percentage / 100.0

  /** Growth function for level based values
    *
    * @param level      >= 0
    * @param base       value at level 0
    * @param growth     > 1.0, value(level+1) = growth*value(level)
    * @param percentage randomness, X -> +-X percents
    * @return base*growth**level +- percentage
    */
  def getRandomValue(level: Int, base: Int, growth: Double, percentage: Int): Int = {
    val value: Double = base * Math.pow(growth, level)
    (value + (getVariation(percentage) * value)).toInt
  }

}
