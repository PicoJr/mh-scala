package game.item

import game.id.Identifiable
import game.item.ArmorPart.ArmorPart
import game.item.element.ElementType
import game.item.status.StatusType

/**
  * Created by nol on 21/11/17.
  */
trait ItemType extends Identifiable {

  /** Get level
    *
    * @return level
    */
  def getLevel: Int

  /** Get name
    *
    * @return name
    */
  def getName: String

  def setName(newName: String): Unit

  /** Get damage provided
    *
    * @return damage provided >= 0
    */
  def getDamage: Int

  /** Get status type
    *
    * @return StatusType, default: NONE
    */
  def getStatusType: StatusType

  /** Get armor provided
    *
    * @return armor provided >= 0
    */
  def getArmor: Int

  /** Get slot type requirements
    *
    * @return slot type requirements
    */
  def getSlotTypeRequirement: SlotTypeRequirements

  /** Get element type
    *
    * @return ElementType, default : NONE
    */
  def getElementType: ElementType

  /** Get charm slots provided
    *
    * @return charm slots provided >= 0
    */
  def getCharmSlotsProvided: Int

  /** Get charm slots required
    *
    * @return charm slots required >= 0
    */
  def getCharmSlotsRequired: Int = {
    getSlotTypeRequirement match {
      case CHARM_SLOT(slot) => slot
      case _ => 0
    }
  }

  /** Same as getArmor > 0
    *
    * @return getArmor > 0
    */
  def hasArmor: Boolean = getArmor > 0

  /** Same as getRawDamage > 0
    *
    * @return getRawDamage > 0
    */
  def hasDamage: Boolean = getDamage > 0

  /** Test if classified as Armor
    *
    * @return is classified as Armor
    */
  def isArmor: Boolean = {
    getSlotTypeRequirement match {
      case ARMOR_SLOT(_) => true
      case _ => false
    }
  }

  /** Test if armorPart slot is required to equip it
    *
    * @param armorPart tested
    * @return true if and only if same armor part is required to equip it
    */
  def isArmorPartRequired(armorPart: ArmorPart): Boolean = {
    getSlotTypeRequirement match {
      case ARMOR_SLOT(part) if part == armorPart => true
      case _ => false
    }
  }

  /** Test if classified as Charm
    *
    * @return classified as Charm
    */
  def isCharm: Boolean = getSlotTypeRequirement match {
    case CHARM_SLOT(_) => true
    case _ => false
  }

  /**
    *
    * @return classified as Equipment
    */
  def isEquipment: Boolean = getSlotTypeRequirement match {
    case CHARM_SLOT(_) | WEAPON_SLOT | ARMOR_SLOT(_) => true
    case _ => false
  }

  /**
    *
    * @return classified as Material (default: false)
    */
  def isMaterial: Boolean = getSlotTypeRequirement match {
    case MATERIAL_SLOT => true
    case _ => false
  }

  /** Test if classified as Weapon
    *
    * @return classified as Weapon
    */
  def isWeapon: Boolean = {
    getSlotTypeRequirement match {
      case WEAPON_SLOT => true
      case _ => false
    }
  }

  /** Same as getCharmSlotsProvided > 0
    *
    * @return charm slots provided > 0
    */
  def providesSlot: Boolean = getCharmSlotsProvided > 0

  /** Same as getCharmSlotsRequired > 0
    *
    * @return charm slots required > 0
    */
  def requiresSlot: Boolean = getCharmSlotsRequired > 0
}
