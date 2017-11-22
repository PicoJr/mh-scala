package game.item

/** Holds items
  * Created by nol on 06/11/17.
  */
class DefaultInventory extends Inventory {

  private var items: Seq[Item] = Seq.empty
  private var equipped: Set[Item] = Set.empty

  def isEquipped(itemId: Long): Boolean = {
    equipped.exists(i => i.getUniqueId == itemId)
  }

  def getItems: Seq[Item] = items

  def findItem(itemId: Long): Option[Item] = {
    items.find(i => i.getUniqueId == itemId)
  }

  def addItems(items: Item*): Unit = {
    this.items ++= items
  }

  def equipItem(itemId: Long): Unit = {
    findItem(itemId) match {
      case Some(i) => if (canBeEquipped(i)) equipItem(i)
      case None =>
    }
  }

  def unEquipItem(itemId: Long): Unit = {
    findItem(itemId) match {
      case Some(i) => equipped = equipped - i
      case None =>
    }
  }

  def removeItem(itemId: Long): Unit = {
    findItem(itemId) match {
      case Some(_) =>
        unEquipItem(itemId)
        items = items.filterNot(i => i.getUniqueId == itemId)
      case None =>
    }
  }

  private def equipItem(item: Item): Unit = {
    equipped += item
  }

  override def canBeEquipped(item: Item): Boolean = {
    if (item.isEquipment) {
      item.getSlotTypeRequirement match {
        case CHARM_SLOT(slot) => (getCharmSlotsUsed + slot) <= getCharmSlotsProvided
        case WEAPON_SLOT() => getWeaponEquipped.isEmpty
        case ARMOR_SLOT(part) => getArmorEquipped(part).isEmpty
        case _ => true
      }
    } else false
  }
}