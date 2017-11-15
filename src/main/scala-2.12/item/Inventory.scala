package item

import item.ArmorPart.ArmorPart
import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 14/11/17.
  */
trait Inventory {

  def isEquipped(item: Item): Boolean

  def isEquipped(itemId: Long): Boolean

  def getItems: Seq[Item]

  def getItem(itemID: Long): Option[Item]

  def getCharmSlotsProvided: Int

  def getCharmSlotsUsed: Int

  def getWeaponEquipped: Option[Item]

  def getArmorEquipped(armorPart: ArmorPart): Option[Item]

  def getItemsEquipped: Seq[Item]

  def getArmorProvided: Int

  def getRawDamageProvided: Int

  def getAttackElementType: ElementType

  def getArmorElementTypes: Seq[ElementType]

  def getAttackStatusType: StatusType

  def getArmorStatusTypes: Seq[StatusType]

  def addItems(items: Item*): Unit

  def equipItem(itemID: Long): Unit

  def unEquipItem(itemId: Long): Unit

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