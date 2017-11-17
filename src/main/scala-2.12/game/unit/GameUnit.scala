package game.unit

import game.Config
import game.id.Identifiable
import game.item.ElementType.ElementType
import game.item.Inventory
import game.item.StatusType.StatusType

/** Game units are either hunters or monsters.
  * Created by nol on 05/11/17.
  */
sealed abstract class GameUnit(name: String) {

  private var _name: String = name

  /** Get game unit name
    *
    * @return game unit name
    */
  def getName: String = _name

  def setName(newName: String): Unit = {
    _name = newName
  }

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

case class Monster(name: String, life: Int, armor: Int, damage: Int, attackStatusType: StatusType, attackElementType: ElementType, armorStatusTypes: Seq[StatusType], armorElementTypes: Seq[ElementType]) extends GameUnit(name) with Identifiable {
  private final val uniqueID = Monster.getNewUniqueMonsterID

  def getUniqueId: Long = uniqueID

  override def getLife: Int = life

  override def getArmor: Int = armor

  override def getDamage: Int = damage

  override def getArmorElementTypes: Seq[ElementType] = armorElementTypes

  override def getAttackStatusType: StatusType = attackStatusType

  override def getArmorStatusTypes: Seq[StatusType] = armorStatusTypes

  override def getAttackElementType: ElementType = attackElementType
}

object Monster {
  private var monsterID: Long = 0

  /**
    *
    * @return new unique id (increasing, not thread safe)
    */
  def getNewUniqueMonsterID: Long = {
    monsterID += 1
    monsterID
  }

}

case class Hunter(name: String, inventory: Inventory) extends GameUnit(name) {

  def this(name: String) {
    this(name, new Inventory)
  }

  /** Get hunter inventory
    *
    * @return hunter inventory
    */
  def getInventory: Inventory = inventory

  def getLife: Int = Config.HUNTER_LIFE_MAX

  def getArmor: Int = getInventory.getArmorProvided

  def getDamage: Int = getInventory.getRawDamageProvided

  def getAttackElementType: ElementType = getInventory.getAttackElementType

  def getArmorElementTypes: Seq[ElementType] = getInventory.getArmorElementTypes

  def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  def getArmorStatusTypes: Seq[StatusType] = getInventory.getArmorStatusTypes
}
