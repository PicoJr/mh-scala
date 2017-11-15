package item

import item.ArmorPart.ArmorPart
import item.Classification.Classification
import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 04/11/17.
  */
class ItemType(name: String, level: Int, damage: Int, statusType: StatusType, armor: Int, slotTypeRequirements: SlotTypeRequirements, classifications: Set[Classification], elementType: ElementType, charmSlots: Int) {

  def this(name: String, level: Int) {
    this(name, level, 0, StatusType.NONE, 0, INVENTORY_SLOT(), Set.empty, ElementType.NONE, 0)
  }

  def getLevel: Int = level

  def getName: String = name

  def getRawDamage: Int = damage

  /**
    *
    * @return StatusType, default: NONE
    */
  def getStatusType: StatusType = statusType

  def getArmor: Int = armor

  def getSlotTypeRequirement: SlotTypeRequirements = slotTypeRequirements

  def getClassifications: Set[Classification] = classifications

  /**
    *
    * @return ElementType, default : NONE
    */
  def getElementType: ElementType = elementType

  def getCharmSlotsProvided: Int = charmSlots

  def getCharmSlotsRequired: Int = {
    getSlotTypeRequirement match {
      case CHARM_SLOT(slot) => slot
      case _ => 0
    }
  }

  def isArmor: Boolean = {
    getSlotTypeRequirement match {
      case ARMOR_SLOT(_) => true
      case _ => false
    }
  }

  def isArmorPart(armorPart: ArmorPart): Boolean = {
    getSlotTypeRequirement match {
      case ARMOR_SLOT(part) if part == armorPart => true
      case _ => false
    }
  }

  def isCharm: Boolean = {
    getSlotTypeRequirement match {
      case CHARM_SLOT(_) => true
      case _ => false
    }
  }

  def isClassifiedAs(classifications: Classification*): Boolean = {
    classifications.foldLeft(true)((found, c) => found & getClassifications.contains(c))
  }

  def isEquipment: Boolean = isClassifiedAs(Classification.EQUIPMENT)

  def isMaterial: Boolean = isClassifiedAs(Classification.MATERIAL)

  def isWeapon: Boolean = {
    getSlotTypeRequirement match {
      case WEAPON_SLOT() => true
      case _ => false
    }
  }

}

object ItemType {

  private var itemID: Long = 0

  def createItem(itemType: ItemType): Item = {
    new Item(itemType)
  }

  def createWeapon(name: String, level: Int, damage: Int): ItemType = {
    Damage(Equipment(new ItemType(name, level), WEAPON_SLOT()), damage)
  }

  def createArmor(name: String, level: Int, armor: Int, armorPart: ArmorPart): ItemType = {
    Protection(Equipment(new ItemType(name, level), ARMOR_SLOT(armorPart)), armor)
  }

  def createCharm(name: String, level: Int, slotsRequired: Int): ItemType = {
    Equipment(new ItemType(name, level), CHARM_SLOT(slotsRequired))
  }

  def createMaterial(name: String, level: Int): ItemType = {
    Material(new ItemType(name, level: Int))
  }

  def getNewUniqueItemID: Long = {
    itemID += 1
    itemID
  }

}







