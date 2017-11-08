package item

import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 05/11/17.
  */
trait Inventory {

  def addItems(items: Item*): Unit

  def getItems: Seq[Item]

  def getCharmSlots: Int

  def getWeaponEquipped: Option[Item]

  def getItemsEquipped: Seq[Item]

  def getArmorProvided: Int

  def getDamageProvided: Int

  def getAttackStatusType: StatusType

  def getArmorStatusTypes: Seq[StatusType]

  def getAttackElementType: ElementType

  def getArmorElementTypes: Seq[ElementType]
}
