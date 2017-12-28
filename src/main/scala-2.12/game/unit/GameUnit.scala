package game.unit

import game.item.element.ElementType
import game.item.status.StatusType

/** Convenience base trait for game units.
  * Created by nol on 22/11/17.
  */
trait GameUnit {

  /** game unit name, not necessarily unique */
  var name: String

  /** unit life (>=0) */
  val life: Int

  /** unit armor (>= 0) */
  val armor: Int

  /** unit damage (>=0) */
  val damage: Int

  /** unit elemental resistances (may be empty) */
  val elementalResistances: Seq[ElementType]

  /** Get unit status resistances (may be empty) */
  val statusResistances: Seq[StatusType]

  /** Get attack element type */
  val attackElementType: ElementType

  /** Get attack status type */
  val attackStatusType: StatusType

}
