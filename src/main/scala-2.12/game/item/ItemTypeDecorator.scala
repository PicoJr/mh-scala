package game.item

import game.item.ElementType.ElementType
import game.item.StatusType.StatusType

/**
  * Note: if an item is decorated twice by the same decorator class,
  * then the last decorator takes precedence over the first one.
  *
  * @param i wrapped
  */
abstract class ItemTypeDecorator(i: ItemTypeTrait) extends ItemType(i.getName, i.getLevel) {
  val item: ItemTypeTrait = i

  override def getDamage: Int = i.getDamage

  override def getStatusType: StatusType = i.getStatusType

  override def getArmor: Int = i.getArmor

  override def getSlotTypeRequirement: SlotTypeRequirements = i.getSlotTypeRequirement

  override def getElementType: ElementType = i.getElementType

  override def getCharmSlotsProvided: Int = i.getCharmSlotsProvided
}

case class Damage(wrapped: ItemTypeTrait, damage: Int) extends ItemTypeDecorator(wrapped) {
  override def getDamage: Int = damage
}

case class Status(wrapped: ItemTypeTrait, statusType: StatusType) extends ItemTypeDecorator(wrapped) {
  override def getStatusType: StatusType = statusType
}

case class Protection(wrapped: ItemTypeTrait, armor: Int) extends ItemTypeDecorator(wrapped) {
  override def getArmor: Int = armor
}

case class Equipment(wrapped: ItemTypeTrait, slotTypeRequirement: SlotTypeRequirements) extends ItemTypeDecorator(wrapped) {

  override def getSlotTypeRequirement: SlotTypeRequirements = slotTypeRequirement
}

case class Element(wrapped: ItemTypeTrait, elementType: ElementType) extends ItemTypeDecorator(wrapped) {
  override def getElementType: ElementType = elementType
}

case class Material(wrapped: ItemTypeTrait) extends ItemTypeDecorator(wrapped) {
  override def getSlotTypeRequirement: SlotTypeRequirements = MATERIAL_SLOT()
}

/**
  *
  * @param wrapped            item
  * @param charmSlotsProvided >= 1
  */
case class CharmSlot(wrapped: ItemTypeTrait, charmSlotsProvided: Int) extends ItemTypeDecorator(wrapped) {
  override def getCharmSlotsProvided: Int = charmSlotsProvided
}

