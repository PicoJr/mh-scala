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
  def getLife: Int

  /** unit armor (>= 0) */
  def getArmor: Int

  /** unit damage (>=0) */
  def getDamage: Int

  /** unit elemental resistances (may be empty) */
  def getElementalResistances: Seq[ElementType]

  /** Get unit status resistances (may be empty) */
  def getStatusResistances: Seq[StatusType]

  /** Get attack element type */
  def getAttackElementType: ElementType

  /** Get attack status type */
  def getAttackStatusType: StatusType

}
