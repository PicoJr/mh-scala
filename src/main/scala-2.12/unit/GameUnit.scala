package unit

import item.ElementType.ElementType
import item.StatusType.StatusType
import item.{ElementType, Inventory, StatusType}

/**
  * Created by nol on 05/11/17.
  */
sealed trait GameUnit {

  def getName: String

  def getLife: Int

  def getArmor: Int

  def getDamage: Int

  def getArmorElementType: Seq[ElementType]

  def getArmorStatusTypes: Seq[StatusType]

  def getAttackElementType: ElementType

  def getAttackStatusType: StatusType

}

object GameUnit {

  def generateName(): String = {
    "not so random" // TODO let the player rename monsters
  }

  def generateMonster(level: Int): Monster = {
    val name = generateName()
    val life = 100
    // TODO procedural
    val armor = 100
    // TODO procedural
    val damage = 100
    // TODO procedural
    val attackStatusType = StatusType.NONE
    // TODO procedural
    val attackElementType = ElementType.NONE
    // TODO procedural
    val armorStatusTypes = Seq.empty
    // TODO procedural
    val armorElementTypes = Seq.empty // TODO procedural
    Monster(name, armor, life, damage, attackStatusType, attackElementType, armorStatusTypes, armorElementTypes)
  }

}

case class Monster(name: String,
                   life: Int,
                   armor: Int,
                   damage: Int,
                   attackStatusType: StatusType,
                   attackElementType: ElementType,
                   armorStatusTypes: Seq[StatusType],
                   armorElementTypes: Seq[ElementType]
                  ) extends GameUnit {

  override def getName: String = name

  override def getLife: Int = life

  override def getArmor: Int = armor

  override def getDamage: Int = damage

  override def getArmorElementType: Seq[ElementType] = armorElementTypes

  override def getAttackStatusType: StatusType = attackStatusType

  override def getArmorStatusTypes: Seq[StatusType] = armorStatusTypes

  override def getAttackElementType: ElementType = attackElementType
}

case class Hunter(name: String, life: Int, inventory: Inventory) extends GameUnit {

  def getInventory: Inventory = inventory

  def getName: String = name

  def getLife: Int = Config.Config.getHunterLife

  def getArmor: Int = getInventory.getArmorProvided

  def getDamage: Int = getInventory.getRawDamageProvided

  def getAttackElementType: ElementType = getInventory.getAttackElementType

  def getArmorElementType: Seq[ElementType] = getInventory.getArmorElementTypes

  def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  def getArmorStatusTypes: Seq[StatusType] = getInventory.getArmorStatusTypes
}
