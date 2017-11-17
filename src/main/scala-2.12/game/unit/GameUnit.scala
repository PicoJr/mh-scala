package game.unit

import game.Config
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType
import game.item.{Inventory, InventoryModel}

/**
  * Created by nol on 05/11/17.
  */
sealed trait GameUnit {

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

  /**
    *
    * @return unit armor element types (may be empty)
    */
  def getArmorElementTypes: Seq[ElementType]

  /**
    *
    * @return unit armor status types (may be empty)
    */
  def getArmorStatusTypes: Seq[StatusType]

  def getAttackElementType: ElementType

  def getAttackStatusType: StatusType

}

case class Monster(name: String, life: Int, armor: Int, damage: Int, attackStatusType: StatusType, attackElementType: ElementType, armorStatusTypes: Seq[StatusType], armorElementTypes: Seq[ElementType]) extends GameUnit {
  private var _name: String = name
  private final val uniqueID = Monster.getNewUniqueMonsterID

  def getUniqueID: Long = uniqueID

  override def getName: String = _name

  override def setName(newName: String): Unit = {
    _name = newName
  }

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

case class Hunter(name: String, inventory: InventoryModel) extends GameUnit {

  private var _name: String = name

  def this(name: String) {
    this(name, new InventoryModel)
  }

  def getInventory: Inventory = inventory

  def getName: String = _name

  def setName(newName: String): Unit = {
    _name = newName
  }

  def getLife: Int = Config.HUNTER_LIFE_MAX

  def getArmor: Int = getInventory.getArmorProvided

  def getDamage: Int = getInventory.getRawDamageProvided

  def getAttackElementType: ElementType = getInventory.getAttackElementType

  def getArmorElementTypes: Seq[ElementType] = getInventory.getArmorElementTypes

  def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  def getArmorStatusTypes: Seq[StatusType] = getInventory.getArmorStatusTypes
}