package game.item.craft.bonus

/**
  * Created by nol on 25/11/17.
  */
trait BonusType {
  val name: String
}

case object DAMAGE extends BonusType {
  override val name = "brutal"
}

case object PROTECTION extends BonusType {
  override val name = "armored"
}
