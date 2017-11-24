package game.item.element

import game.item.element.Effectiveness.Effectiveness
import game.item.element.ElementType.ElementType

/**
  * Created by nol on 22/11/17.
  */
class DefaultEEResolver extends EEResolver {
  override def effectiveness(elementType: ElementType, other: ElementType): Effectiveness = (elementType, other) match {
    case (ElementType.NONE, _) | (_, ElementType.NONE) => Effectiveness.NORMAL
    case (e1, e2) if e1 == e2 => Effectiveness.INEFFECTIVE
    case (ElementType.WATER, ElementType.FIRE) => Effectiveness.EFFECTIVE
    case (ElementType.FIRE, ElementType.WATER) => Effectiveness.INEFFECTIVE
    case _ => Effectiveness.NORMAL
  }
}
