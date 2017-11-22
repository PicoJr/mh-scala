package game.unit

import game.config.ConfigLoader
import game.id.Identifiable
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType
import game.item.{Inventory, InventoryTrait}

/** Game units are either hunters or monsters.
  * Created by nol on 05/11/17.
  */
sealed abstract class GameUnit(name: String) extends GameUnitTrait {

  private var _name: String = name

  def getName: String = _name

  def setName(newName: String): Unit = {
    _name = newName
  }

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

case class Hunter(name: String, inventory: InventoryTrait) extends GameUnit(name) {

  def this(name: String) {
    this(name, new Inventory)
  }

  /** Get hunter inventory
    *
    * @return hunter inventory
    */
  def getInventory: InventoryTrait = inventory

  def getLife: Int = Hunter.HUNTER_LIFE_MAX

  def getArmor: Int = getInventory.getArmorProvided

  def getDamage: Int = getInventory.getDamageProvided

  def getAttackElementType: ElementType = getInventory.getAttackElementType

  def getArmorElementTypes: Seq[ElementType] = getInventory.getArmorElementTypes

  def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  def getArmorStatusTypes: Seq[StatusType] = getInventory.getArmorStatusTypes
}

object Hunter {
  private final val config = ConfigLoader.loadGameConfig

  final val HUNTER_LIFE_MAX: Int = config.getHunterLifeMax
}
