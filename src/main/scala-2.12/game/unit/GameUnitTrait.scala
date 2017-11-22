package game.unit

import game.item.ElementType.ElementType
import game.item.StatusType.StatusType

/**
  * Created by nol on 22/11/17.
  */
trait GameUnitTrait {

  /** Get game unit name
    *
    * @return game unit name
    */
  def getName: String


  def setName(newName: String): Unit

  /**
    *
    * @return unit life (>=0)
    */
  def getLife: Int

  /**
    *
    * @return unit armor (>= 0)
    */
  def getArmor: Int

  /**
    *
    * @return unit damage (>=0)
    */
  def getDamage: Int

  /** Get unit armor element types
    *
    * @return unit armor element types (may be empty)
    */
  def getArmorElementTypes: Seq[ElementType]

  /** Get armor status types
    *
    * @return unit armor status types (may be empty)
    */
  def getArmorStatusTypes: Seq[StatusType]

  /** Get attack element type
    *
    * @return attack element type
    */
  def getAttackElementType: ElementType

  /** Get attack status type
    *
    * @return attack status type
    */
  def getAttackStatusType: StatusType

}
