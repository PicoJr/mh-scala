package unit

import item.ElementType.ElementType
import item.StatusType.StatusType
import item.{ElementType, StatusType}
/**
  * Created by nol on 05/11/17.
  */
class GameUnitDefault(name: String, life: Int) extends GameUnit {

  override def getName: String = name

  override def getLife: Int = life

  override def getArmor: Int = 0

  override def getDamage: Int = 0

  override def getArmorElementType: Seq[ElementType] = Seq.empty

  override def getAttackStatusType: StatusType = StatusType.NONE

  override def getAttackElementType: ElementType = ElementType.NONE

  override def getArmorStatusTypes: Seq[StatusType] = Seq.empty
}
