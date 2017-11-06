package item

import item.ElementType.ElementType

/**
  * Created by nol on 05/11/17.
  */
trait Inventory {
  def getItems: Seq[Item]

  def getCharmSlots: Int

  def getWeaponEquipped: Option[Item]

  def getItemsEquipped: Seq[Item]

  def getArmorProvided: Int

  def getDamageProvided: Int

  def getAttackElementType: ElementType

  def getArmorElementTypes: Seq[ElementType]
}
