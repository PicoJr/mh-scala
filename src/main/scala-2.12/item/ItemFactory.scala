package item

import item.ElementType.ElementType

/**
  * Created by nol on 06/11/17.
  */
object ItemFactory {
  def createDefaultWeapon(name: String, damage: Int) : Item = {
    Weapon(name, damage, 0)
  }

  def createDefaultArmor(name: String, armor: Int) : Item = {
    Armor(name, armor, 0)
  }

  def isEquipment(i: Item): Boolean = i match {
    case _: Equipment => true
    case _ => false
  }

  def isWeapon(i: Item): Boolean = i match {
    case _: Weapon => true
    case _ => false
  }

  def isArmor(i: Item): Boolean = i match {
    case _: Armor => true
    case _ => false
  }

  def isDamage(i: Item): Boolean = i match {
    case _: Damage => true
    case _ => false
  }

  def isProtection(i: Item): Boolean = i match {
    case _: Protection => true
    case _ => false
  }

  def getDamage(i: Item): Int = i match {
    case d : Damage => d.getRawDamage
    case _ => 0
  }

  def getArmor(i: Item): Int = i match {
    case a : Protection => a.getArmor
    case _ => 0
  }

  def isEquipped(i: Item): Boolean = i match {
    case e : Equipment => e.isEquipped
    case _ => false
  }

  def getElementType(i: Item): ElementType = i match {
    case e : Element => e.getElementType
    case _ => ElementType.NONE
  }

  def getCharmSlots(i: Item): Int = i match {
    case c : CharmSlot => c.getCharmSlots
    case _ => 0
  }
}
