package game.item

import game.item.ArmorPart.ArmorPart
import game.item.Classification.Classification
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType

/** A generic blueprint from which items are instantiated.
  *
  * @param name                 for display
  * @param level                >= 0
  * @param damage               provided >= 0
  * @param statusType           provided for attack if isWeapon, protection if isArmor
  * @param armor                provided >= 0
  * @param slotTypeRequirements in order to be equipped
  * @param classifications      distinct, may be empty
  * @param elementType          provide for attack if isWeapon, protection if isArmor
  * @param charmSlots           provided >= 0
  */
class ItemType(name: String, level: Int, damage: Int, statusType: StatusType, armor: Int, slotTypeRequirements: SlotTypeRequirements, classifications: Set[Classification], elementType: ElementType, charmSlots: Int) {

  var _name: String = name

  def this(name: String, level: Int) {
    this(name, level, 0, StatusType.NONE, 0, INVENTORY_SLOT(), Set.empty, ElementType.NONE, 0)
  }

  def this(level: Int) {
    this("unnamed", level, 0, StatusType.NONE, 0, INVENTORY_SLOT(), Set.empty, ElementType.NONE, 0)
  }


  /** Get level
    *
    * @return level
    */
  def getLevel: Int = level

  /** Get name
    *
    * @return name
    */
  def getName: String = _name

  def setName(newName: String): Unit = {
    _name = newName
  }

  /** Get damage provided
    *
    * @return damage provided >= 0
    */
  def getDamage: Int = damage

  /** Get status type
    *
    * @return StatusType, default: NONE
    */
  def getStatusType: StatusType = statusType

  /** Get armor provided
    *
    * @return armor provided >= 0
    */
  def getArmor: Int = armor

  /** Get slot type requirements
    *
    * @return slot type requirements
    */
  def getSlotTypeRequirement: SlotTypeRequirements = slotTypeRequirements

  /** Get classifications
    *
    * @return classifications, may be empty
    */
  def getClassifications: Set[Classification] = classifications

  /** Get element type
    *
    * @return ElementType, default : NONE
    */
  def getElementType: ElementType = elementType

  /** Get charm slots provided
    *
    * @return charm slots provided >= 0
    */
  def getCharmSlotsProvided: Int = charmSlots

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

  /** Same as getElementType != ElementType.NONE
    *
    * @return getElementType != ElementType.NONE
    */
  def hasElementType: Boolean = getElementType != ElementType.NONE

  /** Same as getStatusType != StatusType.NONE
    *
    * @return getStatusType != StatusType.NONE
    */
  def hasStatusType: Boolean = getStatusType != StatusType.NONE

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
  def isCharm: Boolean = {
    getSlotTypeRequirement match {
      case CHARM_SLOT(_) => true
      case _ => false
    }
  }

  /**
    *
    * @param classifications to check
    * @return true if classifications empty or all classifications are found in this.getClassifications.
    *         false otherwise.
    */
  def isClassifiedAs(classifications: Classification*): Boolean = {
    classifications.foldLeft(true)((found, c) => found & getClassifications.contains(c))
  }

  /** Same as isClassifiedAs(Classification.EQUIPMENT)
    *
    * @return classified as Equipment
    */
  def isEquipment: Boolean = isClassifiedAs(Classification.EQUIPMENT)

  /** Same as isClassifiedAs(Classification.MATERIAL)
    *
    * @return classified as Material
    */
  def isMaterial: Boolean = isClassifiedAs(Classification.MATERIAL)

  /** Test if classified as Weapon
    *
    * @return classified as Weapon
    */
  def isWeapon: Boolean = {
    getSlotTypeRequirement match {
      case WEAPON_SLOT() => true
      case _ => false
    }
  }

  /**
    *
    * @return charm slots provided > 0
    */
  def providesSlot: Boolean = getCharmSlotsProvided > 0

  /**
    *
    * @return charm slots required > 0
    */
  def requiresSlot: Boolean = getCharmSlotsRequired > 0
}

object ItemType {

  private var itemID: Long = 0

  /** Create item from itemType
    *
    * @param itemType of item
    * @return item from itemType
    */
  def createItem(itemType: ItemType): Item = {
    new Item(itemType)
  }

  /** Create weapon
    *
    * @param level  of weapon
    * @param damage provided
    * @return weapon s.t. weapon.isWeapon
    */
  def createWeapon(level: Int, damage: Int): ItemType = {
    Damage(Equipment(new ItemType(level), WEAPON_SLOT()), damage)
  }

  /** Create armor
    *
    * @param level     of armor >= 0
    * @param armor     provided >= 0
    * @param armorPart of armor
    * @return armor s.t. armor.isArmor
    */
  def createArmor(level: Int, armor: Int, armorPart: ArmorPart): ItemType = {
    Protection(Equipment(new ItemType(level), ARMOR_SLOT(armorPart)), armor)
  }

  /** Create Charm
    *
    * @param level         of charm >= 0
    * @param slotsRequired by charm >= 1
    * @return charm s.t. charm.isCharm
    */
  def createCharm(level: Int, slotsRequired: Int): ItemType = {
    Equipment(new ItemType(level), CHARM_SLOT(slotsRequired))
  }

  /** Create Material
    *
    * @param name  of material
    * @param level of material >= 0
    * @return material s.t. material.isMaterial
    */
  def createMaterial(name: String, level: Int): ItemType = {
    Material(new ItemType(name, level: Int))
  }

  /** Get new unique id
    * first id is 0
    *
    * @return new unique id, >= 0, increasing
    */
  def getNewUniqueItemID: Long = {
    itemID += 1
    itemID
  }

}







