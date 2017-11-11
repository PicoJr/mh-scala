package item

import item.ArmorPart.ArmorPart
import item.ElementType.ElementType
import item.StatusType.StatusType

/** Holds items
  * Created by nol on 06/11/17.
  */
class Inventory {

  var items: Seq[Item] = Seq.empty

  def getItems: Seq[Item] = items

  def getCharmSlotsProvided: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + Item.getCharmSlotsProvided(i))
  }

  def getCharmSlotsUsed: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + Item.getCharmSlotsRequired(i))
  }

  def getWeaponEquipped: Option[Item] = {
    items.find(i => Item.isWeapon(i) && Item.isEquipped(i))
  }

  def getArmorEquipped(armorPart: ArmorPart): Option[Item] = {
    items.find(i => Item.isArmorPart(i, armorPart))
  }

  def getItemsEquipped: Seq[Item] = {
    items.filter(i => Item.isEquipped(i))
  }

  def getArmorProvided: Int = {
    val equipped : Seq[Item] = getItemsEquipped
    equipped.foldLeft(0)((sum, i) => sum + Item.getArmor(i))
  }

  def getRawDamageProvided: Int = {
    getItemsEquipped.foldLeft(0)((sum, i) => sum + Item.getRawDamage(i))
  }

  def getAttackElementType: ElementType = {
    if (getWeaponEquipped.nonEmpty) {
      Item.getElementType(getWeaponEquipped.get)
    } else {
      ElementType.NONE
    }
  }

  def getArmorElementTypes: Seq[ElementType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => Item.isArmor(i) && Item.isEquipped(i))
    armorPartsEquipped.map(i => Item.getElementType(i))
  }

  def getAttackStatusType: StatusType = {
    if (getWeaponEquipped.nonEmpty) {
      Item.getStatusType(getWeaponEquipped.get)
    } else {
      StatusType.NONE
    }
  }

  def getArmorStatusTypes: Seq[StatusType] = {
    val armorPartsEquipped: Seq[Item] = items.filter(i => Item.isArmor(i) && Item.isEquipped(i))
    armorPartsEquipped.map(i => Item.getStatusType(i))
  }

  def addItems(items: Item*): Unit = {
    this.items ++ items
  }

  def equipItem(item: Item): Unit = {
    if (Item.isEquipment(item)) {
      val equipment = item.asInstanceOf[Equipment]
      equipment.getSlotTypeRequirement match {
        case CHARM_SLOT(slot) => if ((getCharmSlotsUsed + slot) <= getCharmSlotsProvided) {
          equipment.equip()
        }
        case WEAPON_SLOT() => if (getWeaponEquipped.isEmpty) {
          equipment.equip()
        }
        case ARMOR_SLOT(part) => if (getArmorEquipped(part).isEmpty) {
          equipment.equip()
        }
        case _ => equipment.equip()
      }
    } // else cannot be equipped
  }
}
