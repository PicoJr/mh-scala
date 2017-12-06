package game.item.element

/** Element for Attack or Armor
  * Created by nol on 05/11/17.
  */
trait ElementType {
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

