package game.util

import scala.util.Random

/** Procedural util functions
  * Created by nol on 17/11/17.
  */
object Procedural {

  /** Pick random element from sequence
    *
    * @param seq elements to choose from
    * @tparam T any
    * @return Some(random element from seq) if seq non empty else None
    */
  def pickRandomFromSeq[T](seq: Seq[T]): Option[T] = {
    if (seq.nonEmpty) seq.lift(Random.nextInt(seq.size)) else Option.empty
  }

  /** Pick n random elements from sequence if possible.
    * If seq contains less than n elements, then return seq in a random order.
    *
    * @param seq elements to choose from
    * @param n   >= 0, to take if possible (n < 0 is unspecified =P)
    * @tparam T any
    * @return n random elements from seq if possible else seq in a random order.
    */
  def takeRandomFromSeq[T](seq: Seq[T], n: Int): Seq[T] = {
    Random.shuffle(seq).take(n)
  }

  /** Pick random element from elements
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
