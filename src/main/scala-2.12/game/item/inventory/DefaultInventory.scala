package game.item.inventory

import game.item.ArmorPart.ArmorPart
import game.item._

/** Holds items
  * Created by nol on 06/11/17.
  */
class DefaultInventory[TItem <: Item] extends Inventory[TItem] {

  private var items = Seq.empty[TItem]
  private var equipped = Set.empty[TItem]

  private def unEquipWeapon(): Unit = {
    getWeaponEquipped match {
      case Some(i) => unEquipItem(i.getUniqueId)
      case None =>
    }
  }

  private def unEquipArmorPart(armorPart: ArmorPart): Unit = {
    getArmorEquipped(armorPart) match {
      case Some(i) => unEquipItem(i.getUniqueId)
      case None =>
    }
  }

  override def isEquipped(itemId: Long): Boolean = {
    equipped.exists(i => i.getUniqueId == itemId)
  }

  override def getItems: Seq[TItem] = items

  override def findItem(itemId: Long): Option[TItem] = {
    items.find(i => i.getUniqueId == itemId)
  }

  override def addItems(items: TItem*): Unit = {
    this.items ++= items
  }

  override def tryEquipItem(itemId: Long, force: Boolean): Boolean = {
    findItem(itemId) match {
      case Some(i) =>
        if (!canBeEquipped(i) && force) {
          i.getSlotTypeRequirement match {
            case WEAPON_SLOT => unEquipWeapon()
            case ARMOR_SLOT(part) => unEquipArmorPart(part)
            case _ =>
          }
        }
        if (canBeEquipped(i)) {
          equipItem(i)
          true
        } else false
      case None => false
    }
  }

  override def unEquipItem(itemId: Long): Unit = {
    findItem(itemId) match {
      case Some(i) => equipped = equipped - i
      case None =>
    }
  }

  override def removeItem(itemId: Long): Unit = {
    findItem(itemId) match {
      case Some(_) =>
        unEquipItem(itemId)
        items = items.filterNot(i => i.getUniqueId == itemId)
      case None =>
    }
  }

  private def equipItem(item: TItem): Unit = {
    equipped += item
  }

  override def canBeEquipped(item: TItem): Boolean = {
    if (item.isEquipment) {
      item.getSlotTypeRequirement match {
        case CHARM_SLOT(slot) => (getCharmSlotsUsed + slot) <= getCharmSlotsProvided
        case WEAPON_SLOT => getWeaponEquipped.isEmpty
        case ARMOR_SLOT(part) => getArmorEquipped(part).isEmpty
        case _ => true
      }
    } else false
  }
}