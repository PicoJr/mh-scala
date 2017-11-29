package game.item.craft

import game.item.StatusType
import game.item.element.ElementType

/**
  * Created by nol on 29/11/17.
  */
sealed trait AddOn {
}

case object CharmSlotAddOn extends AddOn

case class ElementAddOn(elementType: ElementType) extends AddOn

case class StatusAddOn(statusType: StatusType) extends AddOn

case class BonusAddOn(bonusType: BonusType) extends AddOn
