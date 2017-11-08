package unit

import item.ElementType.ElementType
import item.Inventory
import item.StatusType.StatusType

/**
  * Created by nol on 05/11/17.
  */
abstract class Hunter(name: String, life: Int) extends GameUnitDefault(name: String, life: Int) {
  def getInventory: Inventory

  override def getArmor: Int = getInventory.getArmorProvided

  override def getDamage: Int = getInventory.getDamageProvided

  override def getAttackElementType: ElementType = getInventory.getAttackElementType

  override def getArmorElementType: Seq[ElementType] = getInventory.getArmorElementTypes

  override def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  override def getArmorStatusTypes: Seq[StatusType] = getInventory.getArmorStatusTypes
}
