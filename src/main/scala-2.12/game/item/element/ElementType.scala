package game.item.element

import game.util.Procedural

/** Element for Attack or Armor
  * Created by nol on 05/11/17.
  */
sealed trait ElementType {
  val name: String
}

case object WATER extends ElementType {
  override val name: String = "flowing"
}

case object FIRE extends ElementType {
  override val name: String = "burning"
}

case object ELECTRIC extends ElementType {
  override val name: String = "thundering"
}

case object NORMAL extends ElementType {
  override val name: String = "pure"
}

object ElementType {

  final val values = Seq(WATER, FIRE, ELECTRIC, NORMAL)

  /** Get random element type
    *
    * @return random element type, may be NONE
    */
  def getRandomElementType: ElementType = {
    Procedural.pickRandomFromSeq(values).get
  }

}
