package game.item.status

/** Status type provided by attack or protected against by armor
  * Created by nol on 05/11/17.
  */
trait StatusType {
  /** user-friendly real-play description */
  val name: String
}

case object NEUTRAL extends StatusType {
  override val name: String = "noble"
}

case object SLEEP extends StatusType {
  override val name: String = "tranquilizing"
}

case object STUN extends StatusType {
  override val name = "stunning"
}
