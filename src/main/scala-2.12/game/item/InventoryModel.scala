package game.item

import game.item.ArmorPart.ArmorPart
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType

/** Holds items
  * Created by nol on 06/11/17.
  */
class InventoryModel extends Inventory {

  private var items: Seq[Item] = Seq.empty
  private var equipped: Set[Item] = Set.empty

  def isEquipped(item: Item): Boolean = {
    equipped.contains(item)
  }

  def isEquipped(itemId: Long): Boolean = {
    getItem(itemId) match {
      case Some(i) => isEquipped(i)
      case None => false
    }
  }

  def getItems: Seq[Item] = items

  def getItem(itemID: Long): Option[Item] = {
    items.find(i => i.getUniqueId == itemID)
  }

  def getCharmSlotsProvided: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + i.getCharmSlotsProvided)
  }

  def getCharmSlotsUsed: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + i.getCharmSlotsRequired)
  }

  def getWeaponEquipped: Option[Item] = {
    items.find(i => i.isWeapon && isEquipped(i))
  }

  def getArmorEquipped(armorPart: ArmorPart): Option[Item] = {
    getItemsEquipped.find(i => i.isArmorPartRequired(armorPart))
  }

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
    getItem(itemID) match {
      case Some(i) => equipItem(i)
      case None =>
    }
  }

  def unEquipItem(itemId: Long): Unit = {
    getItem(itemId) match {
      case Some(i) => equipped -= i
      case None =>
    }
  }

  private def equipItem(item: Item): Unit = {
    equipped += item
  }
}