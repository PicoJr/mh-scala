package game.item.element

import game.item.element.Effectiveness.Effectiveness

/**
  * Created by nol on 22/11/17.
  */
class DefaultEEResolver extends EEResolver {
  override def effectiveness(elementType: ElementType, other: ElementType): Effectiveness = (elementType, other) match {
    case (NORMAL, _) | (_, NORMAL) => Effectiveness.NORMAL
    case (e1, e2) if e1 == e2 => Effectiveness.INEFFECTIVE
    case (WATER, FIRE) => Effectiveness.EFFECTIVE
    case (FIRE, WATER) => Effectiveness.INEFFECTIVE
    case _ => Effectiveness.NORMAL
  }
}
