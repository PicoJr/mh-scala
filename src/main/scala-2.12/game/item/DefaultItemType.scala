package game.item

import game.item.ArmorPart.ArmorPart
import game.item.StatusType.StatusType
import game.item.element.ElementType
import game.item.element.ElementType.ElementType

/** A generic blueprint from which items are instantiated.
  *
  * @param name                 for display
  * @param level                >= 0
  * @param damage               provided >= 0
  * @param statusType           provided for attack if isWeapon, protection if isArmor
  * @param armor                provided >= 0
  * @param slotTypeRequirements in order to be equipped
  * @param elementType          provide for attack if isWeapon, protection if isArmor
  * @param charmSlots           provided >= 0
  */
class DefaultItemType(name: String, level: Int, damage: Int, statusType: StatusType, armor: Int, slotTypeRequirements: SlotTypeRequirements, elementType: ElementType, charmSlots: Int) extends ItemType {

  var _name: String = name

  def this(name: String, level: Int) {
    this(name, level, 0, StatusType.NONE, 0, MATERIAL_SLOT(), ElementType.NONE, 0)
  }

  def this(level: Int) {
    this("unnamed", level, 0, StatusType.NONE, 0, MATERIAL_SLOT(), ElementType.NONE, 0)
  }

  def getLevel: Int = level

  def getName: String = _name

  def setName(newName: String): Unit = {
    _name = newName
  }

  def getDamage: Int = damage

  def getStatusType: StatusType = statusType

  def getArmor: Int = armor

  def getSlotTypeRequirement: SlotTypeRequirements = slotTypeRequirements

  def getElementType: ElementType = elementType

  def getCharmSlotsProvided: Int = charmSlots

}

object DefaultItemType {

  /** Create weapon
    *
    * @param level  of weapon
    * @param damage provided
    * @return weapon s.t. weapon.isWeapon
    */
  def createWeapon(level: Int, damage: Int): DefaultItemType = {
    Damage(Equipment(new DefaultItemType(level), WEAPON_SLOT()), damage)
  }

  /** Create armor
    *
    * @param level     of armor >= 0
    * @param armor     provided >= 0
    * @param armorPart of armor
    * @return armor s.t. armor.isArmor
    */
  def createArmor(level: Int, armor: Int, armorPart: ArmorPart): DefaultItemType = {
    Protection(Equipment(new DefaultItemType(level), ARMOR_SLOT(armorPart)), armor)
  }

  /** Create Charm
    *
    * @param level         of charm >= 0
    * @param slotsRequired by charm >= 1
    * @return charm s.t. charm.isCharm
    */
  def createCharm(level: Int, slotsRequired: Int): DefaultItemType = {
    Equipment(new DefaultItemType(level), CHARM_SLOT(slotsRequired))
  }

  /** Create Material
    *
    * @param name  of material
    * @param level of material >= 0
    * @return material s.t. material.isMaterial
    */
  def createMaterial(name: String, level: Int): DefaultItemType = {
    Material(new DefaultItemType(name, level: Int))
  }


}






