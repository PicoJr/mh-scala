package game.item

import game.item.ArmorPart.ArmorPart
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType

/** Holds items
  * Created by nol on 06/11/17.
  */
class Inventory {

  private var items: Seq[Item] = Seq.empty
  private var equipped: Set[Item] = Set.empty

  /** Test if item from inventory is equipped
    *
    * @param item from inventory
    * @return item is equipped (false if not equipped or not in inventory)
    */
  def isEquipped(item: Item): Boolean = {
    equipped.contains(item)
  }

  /** Test if item from inventory is equipped
    *
    * @param itemId of item from inventory
    * @return item is equipped (false if not equipped or not in inventory)
    */
  def isEquipped(itemId: Long): Boolean = {
    findItem(itemId) match {
      case Some(i) => isEquipped(i)
      case None => false
    }
  }

  /** Get all items from inventory
    *
    * @return items from inventory
    */
  def getItems: Seq[Item] = items

  /** Find item with id itemId from inventory
    *
    * @param itemId of item
    * @return item with itemId if found else None
    */
  def findItem(itemId: Long): Option[Item] = {
    items.find(i => i.getUniqueId == itemId)
  }

  /** Charm slots provided by equipped items
    *
    * @return charm slots provided by equipped items
    */
  def getCharmSlotsProvided: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + i.getCharmSlotsProvided)
  }

  /** Charm slots used by equipped items
    *
    * @return charm slots used by equipped items
    */
  def getCharmSlotsUsed: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + i.getCharmSlotsRequired)
  }

  /** Get weapon equipped
    *
    * @return weapon equipped if any else None
    */
  def getWeaponEquipped: Option[Item] = {
    items.find(i => i.isWeapon && isEquipped(i))
  }

  /** Get armor equipped
    *
    * @param armorPart checked
    * @return armor using armor part slot if any else None
    */
  def getArmorEquipped(armorPart: ArmorPart): Option[Item] = {
    getItemsEquipped.find(i => i.isArmorPartRequired(armorPart))
  }

  /** Get items equipped
    *
    * @return items equipped
    */
  def getItemsEquipped: Seq[Item] = {
    items.filter(i => isEquipped(i))
  }

  def getArmorProvided: Int = {
    val equipped: Seq[Item] = getItemsEquipped
    equipped.foldLeft(0)((sum, i) => sum + i.getArmor)
  }

  def getRawDamageProvided: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + i.getDamage)
  }

  def getAttackElementType: ElementType = {
    if (getWeaponEquipped.nonEmpty) {
      getWeaponEquipped.get.getElementType
    } else {
      ElementType.NONE
    }
  }

  def getArmorElementTypes: Seq[ElementType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => i.isArmor && isEquipped(i))
    armorPartsEquipped.map(i => i.getElementType)
  }

  def getAttackStatusType: StatusType = {
    if (getWeaponEquipped.nonEmpty) {
      getWeaponEquipped.get.getStatusType
    } else {
      StatusType.NONE
    }
  }

  def getArmorStatusTypes: Seq[StatusType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => i.isArmor && isEquipped(i))
    armorPartsEquipped.map(i => i.getStatusType)
  }

  def addItems(items: Item*): Unit = {
    this.items ++= items
  }

  def equipItem(itemID: Long): Unit = {
    findItem(itemID) match {
      case Some(i) => equipItem(i)
      case None =>
    }
  }

  def unEquipItem(itemId: Long): Unit = {
    findItem(itemId) match {
      case Some(i) => equipped -= i
      case None =>
    }
  }

  private def equipItem(item: Item): Unit = {
    equipped += item
  }

  def canBeEquipped(item: Item): Boolean = {
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