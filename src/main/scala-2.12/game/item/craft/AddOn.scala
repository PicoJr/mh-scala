package game.item.craft

import game.item.craft.bonus.BonusType
import game.item.element.ElementType
import game.item.status.StatusType

/**
  * Created by nol on 29/11/17.
  */
trait AddOn {
}

case object CharmSlotAddOn extends AddOn

case class ElementAddOn(elementType: ElementType) extends AddOn

case class StatusAddOn(statusType: StatusType) extends AddOn

case class BonusAddOn(bonusType: BonusType) extends AddOn
