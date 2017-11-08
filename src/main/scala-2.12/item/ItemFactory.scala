package item

import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 06/11/17.
  */
object ItemFactory {

  private var itemID: Long = 0

  def getNewUniqueItemID: Long = {
    itemID += 1
    itemID
  }

  def isWeapon(i: Item): Boolean = {
    i.isClassifiedAs(Classification.DAMAGE, Classification.EQUIPMENT)
  }

  def isArmor(i: Item): Boolean = {
    i.isClassifiedAs(Classification.PROTECTION, Classification.EQUIPMENT)
  }

  def getRawDamage(i: Item): Int = {
    if (i.isClassifiedAs(Classification.DAMAGE)) {
      i.asInstanceOf[Damage].getRawDamage
    } else 0
  }

  def getArmor(i: Item): Int = {
    if (i.isClassifiedAs(Classification.PROTECTION)) {
      i.asInstanceOf[Protection].getArmor
    } else 0
  }

  def isEquipped(i: Item): Boolean = {
    if (i.isClassifiedAs(Classification.EQUIPMENT)) {
      i.asInstanceOf[Equipment].isEquipped
    } else false
  }

  def getElementType(i: Item): ElementType = {
    if (i.isClassifiedAs(Classification.ELEMENT)) {
      i.asInstanceOf[Element].getElementType
    } else ElementType.NONE
  }

  def getStatusType(i: Item): StatusType = {
    if (i.isClassifiedAs(Classification.STATUS)) {
      i.asInstanceOf[Status].getStatusType
    } else StatusType.NONE
  }

  def getCharmSlots(i: Item): Int = {
    if (i.isClassifiedAs(Classification.CHARM_SLOT)) {
      i.asInstanceOf[CharmSlot].getCharmSlots
    } else 0
  }
}
