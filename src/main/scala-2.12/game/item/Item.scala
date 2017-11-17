package game.item

import game.id.Identifiable

/** A unique instance of an ItemType identified by an Id
  * Created by nol on 15/11/17.
  */
class Item(itemType: ItemType)
  extends ItemType(itemType.getName, itemType.getLevel, itemType.getDamage, itemType.getStatusType, itemType.getArmor, itemType.getSlotTypeRequirement, itemType.getClassifications, itemType.getElementType, itemType.getCharmSlotsProvided)
    with Identifiable {

  def getItemType: ItemType = itemType

  def isItemType(itemType: ItemType): Boolean = {
    getItemType == itemType
  }

  private final val uniqueID = ItemType.getNewUniqueItemID

  def getUniqueId: Long = uniqueID
}
