package game.item.craft

/**
  * Created by nol on 25/11/17.
  */
sealed trait BonusType {
  val name: String
}

object BonusType {
  final val values = Seq(DAMAGE, PROTECTION)
}

case object DAMAGE extends BonusType {
  override val name = "brutal"
}

case object PROTECTION extends BonusType {
  override val name = "armored"
}
