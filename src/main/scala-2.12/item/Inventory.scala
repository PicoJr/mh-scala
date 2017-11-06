package item

import item.ElementType.ElementType

/**
  * Created by nol on 05/11/17.
  */
trait Inventory {
  def getWeaponEquipped: Option[Item]

  def getItems: Seq[Item]

  def filter(p: (Item) => Boolean) : Seq[Item]

  def getArmorProvided: Int

  def getDamageProvided: Int

  def getAttackElement: ElementType

  def getArmorElement: ElementType
}
