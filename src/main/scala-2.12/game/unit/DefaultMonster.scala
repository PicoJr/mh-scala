package game.unit

import game.item.element.ElementType
import game.item.status.StatusType

/**
  * Created by nol on 06/12/17.
  */
case class DefaultMonster(name: String, uniqueId: Long, life: Int, armor: Int, damage: Int, attackStatusType: StatusType, attackElementType: ElementType, armorStatusTypes: Seq[StatusType], armorElementTypes: Seq[ElementType]) extends DefaultGameUnit(name) with Monster {

  override def getUniqueId: Long = uniqueId

  override def getLife: Int = life

  override def getArmor: Int = armor

  override def getDamage: Int = damage

  override def getElementalResistances: Seq[ElementType] = armorElementTypes

  override def getAttackStatusType: StatusType = attackStatusType

  override def getStatusResistances: Seq[StatusType] = armorStatusTypes

  override def getAttackElementType: ElementType = attackElementType
}
