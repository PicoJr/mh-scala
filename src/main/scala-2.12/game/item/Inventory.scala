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
    isEquipped(item.getUniqueId)
  }

  /** Test if item from inventory is equipped
    *
    * @param itemId of item from inventory
    * @return item is equipped (false if not equipped or not in inventory)
    */
  def isEquipped(itemId: Long): Boolean = {
    equipped.exists(i => i.getUniqueId == itemId)
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

  /** Compute armor provided by equipped items from inventory
    *
    * @return armor provided by equipped items
    */
  def getArmorProvided: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + i.getArmor)
  }

  /** Compute damage provided by equipped items from inventory
    *
    * @return damage provided by equipped items
    */
  def getDamageProvided: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + i.getDamage)
  }

  /** Get attack element type provided by equipped weapon if any
    * if no weapon equipped => NONE
    *
    * @return attack element type provided by equipped weapon if any
    */
  def getAttackElementType: ElementType = {
    getWeaponEquipped match {
      case Some(w) => w.getElementType
      case None => ElementType.NONE
    }
  }

  /** Get armor element types provided by equipped armor parts if any
    * if no armor parts equipped => Seq.empty
    *
    * @return armor element types provided by equipped armor parts
    */
  def getArmorElementTypes: Seq[ElementType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => i.isArmor && isEquipped(i))
    armorPartsEquipped.map(i => i.getElementType)
  }

  /** Get attack status type provided by equipped weapon if any
    * if no weapon equipped => NONE
    *
    * @return attack status type provided by equipped weapon if any
    */
  def getAttackStatusType: StatusType = {
    getWeaponEquipped match {
      case Some(w) => w.getStatusType
      case None => StatusType.NONE
    }
  }

  /** Get armor status types resistances provided by equipped armor parts if any
    * if no armor parts equipped => Seq.empty
    *
    * @return armor status types resistance provided by equipped armor parts
    */
  def getArmorStatusTypes: Seq[StatusType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => i.isArmor && isEquipped(i))
    armorPartsEquipped.map(i => i.getStatusType)
  }

  /** Add items to inventory
    *
    * @param items to add
    */
  def addItems(items: Item*): Unit = {
    this.items ++= items
  }

  /** Equip item with id itemId from inventory if any and possible
    *
    * @param itemId of item to equip
    */
  def equipItem(itemId: Long): Unit = {
    findItem(itemId) match {
      case Some(i) => if (canBeEquipped(i)) equipItem(i)
      case None =>
    }
  }

  /** Un-equip item with id itemId if any
    *
    * @param itemId of item to un-equip
    */
  def unEquipItem(itemId: Long): Unit = {
    findItem(itemId) match {
      case Some(i) => equipped = equipped - i
      case None =>
    }
  }

  /** Remove and un-equip item with id itemId from inventory if any
    *
    * @param itemId of item to remove
    */
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

  /** Check item can be equipped i.e. is an equipment and complies with slot requirements
    *
    * @param item to check
    * @return item can be equipped
    */
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