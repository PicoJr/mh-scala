package game.item

import game.item.element.ElementType
import game.item.status.StatusType

/** A unique instance of an ItemType identified by an Id
  *
  */
class DefaultItem(itemType: ItemType, itemId: Long) extends Item {

  override def getItemTypeId: Id = itemType.getUniqueId

  override def getUniqueId: Long = itemId

  override def getLevel: Int = itemType.getLevel

  override def getName: String = itemType.getName

  override def setName(newName: String): Unit = itemType.setName(newName)

  override def getDamage: Int = itemType.getDamage

  override def getStatusType: StatusType = itemType.getStatusType

  override def getArmor: Int = itemType.getArmor

  override def getSlotTypeRequirement: SlotTypeRequirements = itemType.getSlotTypeRequirement

  override def getElementType: ElementType = itemType.getElementType

  override def getCharmSlotsProvided: Int = itemType.getCharmSlotsProvided
}
