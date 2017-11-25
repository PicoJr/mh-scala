package game.item

import game.util.Procedural

/** Status type provided attack or protected against by armor
  * Created by nol on 05/11/17.
  */
sealed trait StatusType {
  val name: String
}

case object STUN extends StatusType {
  override val name = "stunning"
}

case object SLEEP extends StatusType {
  override val name: String = "tranquilizing"
}

case object NEUTRAL extends StatusType {
  override val name: String = "noble"
}

object StatusType {

  final val values = Seq(STUN, SLEEP, NEUTRAL)

  def getRandomStatusType: StatusType = {
    Procedural.pickRandomFromSeq(StatusType.values).get
  }

}
