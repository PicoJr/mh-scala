package game.item

import game.item.Effectiveness.Effectiveness

import scala.util.Random

/** Element for Attack or Armor
  * Created by nol on 05/11/17.
  */
object ElementType extends Enumeration {
  type ElementType = Value
  val NONE, WATER, FIRE = Value

  /** Get random element type
    *
    * @return random element type, may be NONE
    */
  def getRandomElementType: ElementType = {
    ElementType(Random.nextInt(ElementType.maxId))
  }

  /** Get effectiveness of attack with element type against other element type
    *
    * @param elementType of attack
    * @param other       element type
    * @return effectiveness of element type vs other
    */
  def effectiveness(elementType: ElementType, other: ElementType): Effectiveness = (elementType, other) match {
    case (NONE, _) | (_, NONE) => Effectiveness.NORMAL
    case (e1, e2) if e1 == e2 => Effectiveness.INEFFECTIVE
    case (WATER, FIRE) => Effectiveness.EFFECTIVE
    case (FIRE, WATER) => Effectiveness.INEFFECTIVE
    case _ => Effectiveness.NORMAL
  }

  /** Get multiplier from attack with element type vs other element type
    *
    * @param elementType of attack
    * @param other       element type
    * @return Effectiveness.multiplier(effectiveness(element, other))
    */
  def multiplier(elementType: ElementType, other: ElementType): Double = {
    Effectiveness.multiplier(effectiveness(elementType, other))
  }
}
