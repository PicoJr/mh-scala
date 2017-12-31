package game.item

import game.item.ArmorPart.ArmorPart

/**
  * Created by nol on 17/12/17.
  */
trait AbstractItemTypeFactory[TItemType <: ItemType] {

  /** Create weapon
    *
    * @param level  of weapon
    * @param damage provided
    * @return weapon s.t. weapon.isWeapon
    */
  def createWeapon(level: Int, damage: Int): TItemType

  /** Create armor
    *
    * @param level     of armor >= 0
    * @param armor     provided >= 0
    * @param armorPart of armor
    * @return armor s.t. armor.isArmor
    */
  def createArmor(level: Int, armor: Int, armorPart: ArmorPart): TItemType

  /** Create Charm
    *
    * @param level         of charm >= 0
    * @param slotsRequired by charm >= 1
    * @return charm s.t. charm.isCharm
    */
  def createCharm(level: Int, slotsRequired: Int): TItemType

  /** Create Material
    *
    * @param name  of material
    * @param level of material >= 0
    * @return material s.t. material.isMaterial
    */
  def createMaterial(name: String, level: Int): TItemType
}
