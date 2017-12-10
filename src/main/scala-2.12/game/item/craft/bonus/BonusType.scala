package game.item.craft.bonus

/** ItemType Bonus.
  * Not sealed, for extension purpose.
  * Created by nol on 25/11/17.
  */
trait BonusType {
  /** user-friendly real-play description */
  val name: String
}

case object DAMAGE extends BonusType {
  override val name = "brutal"
}

case object PROTECTION extends BonusType {
  override val name = "armored"
}
