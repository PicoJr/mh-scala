package unit

import item.ElementType
import item.ElementType.ElementType
import unit.StatusType.StatusType
/**
  * Created by nol on 05/11/17.
  */
class GameUnitDefault(name: String, life: Int) extends GameUnit {

  override def getStatus: StatusType = StatusType.NORMAL

  override def getName: String = name

  override def getLife: Int = life

  override def getArmor: Int = 0

  override def getDamage: Int = 0

  override def getElement: ElementType = ElementType.NONE
}
