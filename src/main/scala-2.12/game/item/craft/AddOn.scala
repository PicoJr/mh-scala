package game.item.craft

import game.item.craft.bonus.BonusType
import game.item.element.ElementType
import game.item.status.StatusType

/** ItemType AddOn.
  * Stacked up to make increasingly complex itemTypes.
  * Created by nol on 29/11/17.
  */
trait AddOn {
}

/** Provides charm slot. */
case object CharmSlotAddOn extends AddOn

/** Provides elemental protection/damage.
  *
  * @param elementType added
  */
case class ElementAddOn(elementType: ElementType) extends AddOn

/** Provides status attack effect/protection.
  *
  * @param statusType added
  */
case class StatusAddOn(statusType: StatusType) extends AddOn

/** Provides additional bonus.
  *
  * @param bonusType added.
  */
case class BonusAddOn(bonusType: BonusType) extends AddOn
