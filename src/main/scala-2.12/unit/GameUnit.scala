package unit

import item.ElementType.ElementType
import item.StatusType.StatusType
import item.{ElementType, Inventory, InventoryModel, StatusType}

/**
  * Created by nol on 05/11/17.
  */
sealed trait GameUnit {

  def getName: String

  def setName(newName: String): Unit

  def getLife: Int

  def getArmor: Int

  def getDamage: Int

  def getArmorElementType: Seq[ElementType]

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

  override def getArmorElementType: Seq[ElementType] = armorElementTypes

  override def getAttackStatusType: StatusType = attackStatusType

  override def getArmorStatusTypes: Seq[StatusType] = armorStatusTypes

  override def getAttackElementType: ElementType = attackElementType
}

object Monster {
  private var monsterID: Long = 0

  def getNewUniqueMonsterID: Long = {
    monsterID += 1
    monsterID
  }

  def generateName(): String = {
    "m" // TODO let the player rename monsters
  }

  def generateMonster(level: Int): Monster = {
    val name = generateName()
    // TODO procedural
    val life = 100
    // TODO procedural
    val armor = 100
    // TODO procedural
    val damage = 100
    val attackStatusType = StatusType.getRandomStatusType
    val attackElementType = ElementType.getRandomElementType
    val armorStatusTypes = Seq(StatusType.getRandomStatusType)
    val armorElementTypes = Seq(attackElementType, ElementType.getRandomElementType)
    Monster(name, armor, life, damage, attackStatusType, attackElementType, armorStatusTypes, armorElementTypes)
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

  def getLife: Int = config.Config.HUNTER_LIFE_MAX

  def getArmor: Int = getInventory.getArmorProvided

  def getDamage: Int = getInventory.getRawDamageProvided

  def getAttackElementType: ElementType = getInventory.getAttackElementType

  def getArmorElementType: Seq[ElementType] = getInventory.getArmorElementTypes

  def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  def getArmorStatusTypes: Seq[StatusType] = getInventory.getArmorStatusTypes
}
