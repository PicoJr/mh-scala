package unit

import item.ElementType
import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 05/11/17.
  */
trait GameUnit {
  def getStatus: StatusType

  def getName: String

  def getLife: Int

  def getArmor: Int

  def getDamage: Int

  def getArmorElementType: Seq[ElementType] = Seq.empty

  def getAttackElementType: ElementType = ElementType.NONE
}
