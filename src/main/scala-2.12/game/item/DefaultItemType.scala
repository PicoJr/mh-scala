package game.item

import game.item.element.{ElementType, NORMAL}
import game.item.status.{NEUTRAL, StatusType}

/** A generic blueprint from which items are instantiated.
  *
  * @param name                 for display
  * @param level                >= 0
  * @param damage               provided >= 0
  * @param statusType           provided for attack if isWeapon, protection if isArmor
  * @param armor                provided >= 0
  * @param slotTypeRequirements in order to be equipped
  * @param elementType          provide for attack if isWeapon, protection if isArmor
  * @param charmSlots           provided >= 0
  */
class DefaultItemType(name: String, level: Int, damage: Int, statusType: StatusType, armor: Int, slotTypeRequirements: SlotTypeRequirements, elementType: ElementType, charmSlots: Int, itemTypeId: Long) extends ItemType {

  var _name: String = name

  def this(name: String, level: Int, itemTypeId: Long) {
    this(name, level, 0, NEUTRAL, 0, MATERIAL_SLOT, NORMAL, 0, itemTypeId)
  }

  def this(level: Int, itemTypeId: Long) {
    this("unnamed", level, 0, NEUTRAL, 0, MATERIAL_SLOT, NORMAL, 0, itemTypeId)
  }

  override def getLevel: Int = level

  override def getName: String = _name

  override def setName(newName: String): Unit = {
    _name = newName
  }

  override def getDamage: Int = damage

  override def getStatusType: StatusType = statusType

  override def getArmor: Int = armor

  override def getSlotTypeRequirement: SlotTypeRequirements = slotTypeRequirements

  override def getElementType: ElementType = elementType

  override def getCharmSlotsProvided: Int = charmSlots

  override def getUniqueId: Long = itemTypeId
}
