package game.item.inventory

import game.id.Identifiable
import game.item.ArmorPart.ArmorPart
import game.item.Item
import game.item.element.{ElementType, NORMAL}
import game.item.status.{NEUTRAL, StatusType}

/**
  * Created by nol on 21/11/17.
  */
trait Inventory[TItem <: Item] {

  type Id = Identifiable.Id

  /** Test if item from inventory is equipped
    *
    * @param item from inventory
    * @return item is equipped (false if not equipped or not in inventory)
    */
  def isEquipped(item: TItem): Boolean = {
    isEquipped(item.getUniqueId)
  }

  /** Test if item from inventory is equipped
    *
    * @param itemId of item from inventory
    * @return item is equipped (false if not equipped or not in inventory)
    */
  def isEquipped(itemId: Id): Boolean

  /** Get all items from inventory
    *
    * @return items from inventory
    */
  def getItems: Seq[TItem]

  /** Find item with id itemId from inventory
    *
    * @param itemId of item
    * @return item with itemId if found else None
    */
  def findItem(itemId: Id): Option[TItem]

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
  def getWeaponEquipped: Option[TItem] = {
    getItemsEquipped.find(i => i.isWeapon && isEquipped(i))
  }

  /** Get armor equipped
    *
    * @param armorPart checked
    * @return armor using armor part slot if any else None
    */
  def getArmorEquipped(armorPart: ArmorPart): Option[TItem] = {
    getItemsEquipped.find(i => i.isArmorPartRequired(armorPart))
  }

  /** Get all charms equipped
    *
    * @return charms equipped
    */
  def getCharmsEquipped: Seq[TItem] = {
    getItemsEquipped.filter(i => i.isCharm)
  }

  /** Get items equipped
    *
    * @return items equipped
    */
  def getItemsEquipped: Seq[TItem] = {
    getItems.filter(i => isEquipped(i))
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
    * if no weapon equipped => NORMAL
    *
    * @return attack element type provided by equipped weapon if any
    */
  def getAttackElementType: ElementType = {
    getWeaponEquipped match {
      case Some(w) => w.getElementType
      case None => NORMAL
    }
  }

  /** Get armor element types provided by equipped armor parts if any
    * if no armor parts equipped => Seq.empty
    *
    * @return armor element types provided by equipped armor parts
    */
  def getArmorElementTypes: Seq[ElementType] = {
    val armorPartsEquipped = getItemsEquipped.filter(i => i.isArmor)
    armorPartsEquipped.map(i => i.getElementType)
  }

  /** Get charms element types provided by equipped charms
    * if no charms equipped => Seq.empty
    *
    * @return charms element types provided by equipped charms
    */
  def getCharmsElementTypes: Seq[ElementType] = {
    val charmsEquipped = getCharmsEquipped
    charmsEquipped.map(i => i.getElementType)
  }

  /** Get charms status types provided by equipped charms
    * if no charms equipped => Seq.empty
    *
    * @return charms status types provided by equipped charms
    */
  def getCharmsStatusTypes: Seq[StatusType] = {
    val charmsEquipped = getCharmsEquipped
    charmsEquipped.map(i => i.getStatusType)
  }

  /** Get attack status type provided by equipped weapon if any
    * if no weapon equipped => NEUTRAL
    *
    * @return attack status type provided by equipped weapon if any
    */
  def getAttackStatusType: StatusType = {
    getWeaponEquipped match {
      case Some(w) => w.getStatusType
      case None => NEUTRAL
    }
  }

  /** Get armor status types resistances provided by equipped armor parts if any
    * if no armor parts equipped => Seq.empty
    *
    * @return armor status types resistance provided by equipped armor parts
    */
  def getArmorStatusTypes: Seq[StatusType] = {
    val armorPartsEquipped = getItemsEquipped.filter(i => i.isArmor)
    armorPartsEquipped.map(i => i.getStatusType)
  }

  /** Add items to inventory
    *
    * @param items to add
    */
  def addItems(items: TItem*): Unit

  /** Equip item with id itemId from inventory if any and possible
    *
    * @param itemId of item to equip
    * @param force  item to be equipped (un-equip other items if necessary)
    * @return item with id itemId equipped successfully
    */
  def tryEquipItem(itemId: Id, force: Boolean): Boolean

  /** Un-equip item with id itemId if any
    *
    * @param itemId of item to un-equip
    */
  def unEquipItem(itemId: Id): Unit

  /** Remove and un-equip item with id itemId from inventory if any
    *
    * @param itemId of item to remove
    */
  def removeItem(itemId: Id): Unit

  /** Check item can be equipped i.e. is an equipment and complies with slot requirements
    *
    * @param item to check
    * @return item can be equipped
    */
  def canBeEquipped(item: TItem): Boolean
}
