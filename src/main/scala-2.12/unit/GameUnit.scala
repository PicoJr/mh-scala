package unit

import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 05/11/17.
  */
trait GameUnit {

  def getName: String

  def getLife: Int

  def getArmor: Int

  def getDamage: Int

  def getArmorElementType: Seq[ElementType]

  def getArmorStatusTypes: Seq[StatusType]

  def getAttackElementType: ElementType

  def getAttackStatusType: StatusType
}
