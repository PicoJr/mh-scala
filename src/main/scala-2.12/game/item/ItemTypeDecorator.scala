package game.item

import game.item.Classification.Classification
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType

/**
  * Note: if an item is decorated twice by the same decorator class,
  * then the last decorator takes precedence over the first one.
  *
  * @param i wrapped
  * @param c classifications obtained with decorator
  */
abstract class ItemTypeDecorator(i: ItemType, c: Classification*) extends ItemType(i.getName, i.getLevel) {
  val item: ItemType = i

  override def getClassifications: Set[Classification] = i.getClassifications ++ c

  override def getDamage: Int = i.getDamage

  override def getStatusType: StatusType = i.getStatusType

  override def getArmor: Int = i.getArmor

  override def getSlotTypeRequirement: SlotTypeRequirements = i.getSlotTypeRequirement

  override def getElementType: ElementType = i.getElementType

  override def getCharmSlotsProvided: Int = i.getCharmSlotsProvided
}

case class Damage(wrapped: ItemType, damage: Int) extends ItemTypeDecorator(wrapped, Classification.DAMAGE) {
  override def getDamage: Int = damage
}

case class Status(wrapped: ItemType, statusType: StatusType) extends ItemTypeDecorator(wrapped, Classification.STATUS) {
  override def getStatusType: StatusType = statusType
}

case class Protection(wrapped: ItemType, armor: Int) extends ItemTypeDecorator(wrapped, Classification.PROTECTION) {
  override def getArmor: Int = armor
}

case class Equipment(wrapped: ItemType, slotTypeRequirement: SlotTypeRequirements) extends ItemTypeDecorator(wrapped, Classification.EQUIPMENT) {

  override def getSlotTypeRequirement: SlotTypeRequirements = slotTypeRequirement
}

case class Element(wrapped: ItemType, elementType: ElementType) extends ItemTypeDecorator(wrapped, Classification.ELEMENT) {
  override def getElementType: ElementType = elementType
}

case class Material(wrapped: ItemType) extends ItemTypeDecorator(wrapped, Classification.MATERIAL)

/**
  *
  * @param wrapped            item
  * @param charmSlotsProvided >= 1
  */
case class CharmSlot(wrapped: ItemType, charmSlotsProvided: Int) extends ItemTypeDecorator(wrapped, Classification.CHARM_SLOT) {
  override def getCharmSlotsProvided: Int = charmSlotsProvided
}

