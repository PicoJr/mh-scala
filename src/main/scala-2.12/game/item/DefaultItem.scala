package game.item

import game.id.DefaultIdSupplier
import game.item.StatusType.StatusType
import game.item.element.ElementType.ElementType

/** A unique instance of an ItemType identified by an Id
  *
  */
class DefaultItem(itemType: ItemType) extends Item {

  private final val uniqueID = DefaultItem.itemIdSupplier.getNextUniqueId

  override def getItemType: ItemType = itemType

  override def getUniqueId: Long = uniqueID

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

object DefaultItem {

  private val itemIdSupplier = new DefaultIdSupplier

  /** Create item from itemType
    *
    * @param itemType of item
    * @return item from itemType
    */
  def createItem(itemType: ItemType): Item = {
    new DefaultItem(itemType)
  }
}
