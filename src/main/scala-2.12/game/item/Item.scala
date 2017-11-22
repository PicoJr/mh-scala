package game.item

import game.item.ElementType.ElementType
import game.item.StatusType.StatusType

/** A unique instance of an ItemType identified by an Id
  *
  */
class Item(itemType: ItemTypeTrait) extends ItemTrait {

  private final val uniqueID = Item.getNewUniqueItemID

  def getItemType: ItemTypeTrait = itemType

  def getUniqueId: Long = uniqueID

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

object Item {

  private var itemID: Long = 0

  /** Create item from itemType
    *
    * @param itemType of item
    * @return item from itemType
    */
  def createItem(itemType: ItemTypeTrait): ItemTrait = {
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
