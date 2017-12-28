package game.unit

import game.item.element.ElementType
import game.item.status.StatusType

/**
  * Created by nol on 06/12/17.
  */
case class DefaultMonster(monsterName: String, uniqueId: Long, monsterLife: Int, monsterArmor: Int, monsterDamage: Int, monsterAttackStatusType: StatusType, monsterAttackElementType: ElementType, monsterArmorStatusTypes: Seq[StatusType], monsterArmorElementTypes: Seq[ElementType]) extends Monster {

  override def getUniqueId: Long = uniqueId

  override var name: String = monsterName

  override val life: Int = monsterLife

  override val armor: Int = monsterArmor

  override val damage: Int = monsterDamage

  override val elementalResistances: Seq[ElementType] = monsterArmorElementTypes

  override val attackStatusType: StatusType = monsterAttackStatusType

  override val statusResistances: Seq[StatusType] = monsterArmorStatusTypes

  override val attackElementType: ElementType = monsterAttackElementType
}
