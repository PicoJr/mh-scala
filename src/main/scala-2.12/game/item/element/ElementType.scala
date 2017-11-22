package game.item.element

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

}
