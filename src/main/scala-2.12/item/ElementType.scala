package item

import item.Effectiveness.Effectiveness

/**
  * Created by nol on 05/11/17.
  */
object ElementType extends Enumeration {
  type ElementType = Value
  val NONE, WATER, FIRE = Value

  def getRandomElementType: ElementType = {
    ElementType(util.Random.nextInt(ElementType.maxId))
  }

  def effectiveness(element: ElementType, other: ElementType): Effectiveness = (element, other) match {
    case (NONE, _) | (_, NONE) => Effectiveness.NORMAL
    case (e1, e2) if e1 == e2 => Effectiveness.INEFFECTIVE
    case (WATER, FIRE) => Effectiveness.EFFECTIVE
    case (FIRE, WATER) => Effectiveness.INEFFECTIVE
    case _ => Effectiveness.NORMAL
  }

  def multiplier(element: ElementType, other: ElementType): Double = {
    Effectiveness.multiplier(effectiveness(element, other))
  }
}
