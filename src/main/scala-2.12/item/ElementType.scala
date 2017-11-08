package item

import item.Effectiveness.Effectiveness

/**
  * Created by nol on 05/11/17.
  */
object ElementType extends Enumeration {
  type ElementType = Value
  val NONE, WATER, FIRE = Value

  def effectiveness(element: ElementType, other: ElementType): Effectiveness = (element, other) match {
    case (NONE, _) | (_, NONE) => Effectiveness.NORMAL
    case (e1, e2) if e1 == e2 => Effectiveness.INEFFECTIVE
    case (WATER, FIRE) => Effectiveness.EFFECTIVE
    case (FIRE, WATER) => Effectiveness.INEFFECTIVE
    case _ => Effectiveness.NORMAL
  }

  def multiplier(element: ElementType, other: ElementType): Double = {
    multiplier(effectiveness(element, other))
  }

  def multiplier(effectiveness: Effectiveness): Double = effectiveness match {
    case Effectiveness.NORMAL => 1.0
    case Effectiveness.INEFFECTIVE => 0.5
    case Effectiveness.EFFECTIVE => 2.0
  }
}
