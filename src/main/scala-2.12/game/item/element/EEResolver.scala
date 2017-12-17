package game.item.element

import game.item.element.Effectiveness.Effectiveness

/** Element Effectiveness Resolver (EEResolver)
  * Created by nol on 22/11/17.
  */
trait EEResolver {

  /** Get effectiveness of attack with element type against other element type
    *
    * @param elementType of attack
    * @param other       element type
    * @return effectiveness of element type vs other
    */
  def effectiveness(elementType: ElementType, other: ElementType): Effectiveness

  /** Get multiplier from attack with element type vs other element type
    *
    * @param elementType of attack
    * @param other       element type
    * @return Effectiveness.multiplier(effectiveness(element, other))
    */
  def multiplier(elementType: ElementType, other: ElementType): Double
}
