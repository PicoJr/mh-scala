package game.item

import game.id.Identifiable

/** A unique instance of an ItemType identified by an Id
  *
  */
class Item(itemType: ItemType)
  extends ItemType(itemType.getName, itemType.getLevel, itemType.getDamage, itemType.getStatusType, itemType.getArmor, itemType.getSlotTypeRequirement, itemType.getClassifications, itemType.getElementType, itemType.getCharmSlotsProvided)
    with Identifiable {

  private final val uniqueID = ItemType.getNewUniqueItemID

  /** Get item type
    *
    * @return item type
    */
  def getItemType: ItemType = itemType

  /** Same as getItemType == itemType
    *
    * @param itemType checked
    * @return getItemType == itemType
    */
  def isItemType(itemType: ItemType): Boolean = {
    getItemType == itemType
  }

  def getUniqueId: Long = uniqueID
}

object Item {

  /** Create item from itemType
    *
    * @param itemType of item
    * @return item from itemType
    */
  def createItem(itemType: ItemType): Item = {
    new Item(itemType)
  }
}
