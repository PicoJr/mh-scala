package game.item

import game.id.Identifiable

/** A unique instance of an ItemType identified by an Id
  *
  */
class Item(itemType: ItemType)
  extends ItemType(itemType.getName, itemType.getLevel, itemType.getDamage, itemType.getStatusType, itemType.getArmor, itemType.getSlotTypeRequirement, itemType.getElementType, itemType.getCharmSlotsProvided)
    with Identifiable {

  private final val uniqueID = Item.getNewUniqueItemID

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

  private var itemID: Long = 0

  /** Create item from itemType
    *
    * @param itemType of item
    * @return item from itemType
    */
  def createItem(itemType: ItemType): Item = {
    new Item(itemType)
  }

  /** Get new unique id
    * first id is 0
    *
    * @return new unique id, >= 0, increasing
    */
  def getNewUniqueItemID: Long = {
    itemID += 1
    itemID
  }
}
