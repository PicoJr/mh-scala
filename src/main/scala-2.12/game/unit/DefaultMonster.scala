package game.unit

import game.item.element.ElementType
import game.item.status.StatusType

/**
  * Created by nol on 06/12/17.
  */
case class DefaultMonster(monsterName: String, uniqueId: Long, monsterLife: Int, monsterArmor: Int, monsterDamage: Int, monsterAttackStatusType: StatusType, monsterAttackElementType: ElementType, monsterArmorStatusTypes: Seq[StatusType], monsterArmorElementTypes: Seq[ElementType]) extends Monster {

  override def getUniqueId: Long = uniqueId

  override var name: String = monsterName

  override def getLife: Int = monsterLife

  override def getArmor: Int = monsterArmor

  override def getDamage: Int = monsterDamage

  override def getElementalResistances: Seq[ElementType] = monsterArmorElementTypes

  override def getAttackStatusType: StatusType = monsterAttackStatusType

  override def getStatusResistances: Seq[StatusType] = monsterArmorStatusTypes

  override def getAttackElementType: ElementType = monsterAttackElementType
}
