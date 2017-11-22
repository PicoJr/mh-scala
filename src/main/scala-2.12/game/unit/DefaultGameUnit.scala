package game.unit

import game.config.ConfigLoader
import game.id.DefaultIdSupplier
import game.item.StatusType.StatusType
import game.item.element.ElementType.ElementType
import game.item.{DefaultInventory, Inventory}

/** Game units are either hunters or monsters.
  * Created by nol on 05/11/17.
  */
sealed abstract class DefaultGameUnit(name: String) extends GameUnit {

  private var _name: String = name

  def getName: String = _name

  def setName(newName: String): Unit = {
    _name = newName
  }

}

case class DefaultMonster(name: String, life: Int, armor: Int, damage: Int, attackStatusType: StatusType, attackElementType: ElementType, armorStatusTypes: Seq[StatusType], armorElementTypes: Seq[ElementType]) extends DefaultGameUnit(name) with Monster {
  private final val uniqueID = DefaultMonster.monsterIdSupplier.getNextUniqueId

  def getUniqueId: Long = uniqueID

  override def getLife: Int = life

  override def getArmor: Int = armor

  override def getDamage: Int = damage

  override def getArmorElementTypes: Seq[ElementType] = armorElementTypes

  override def getAttackStatusType: StatusType = attackStatusType

  override def getArmorStatusTypes: Seq[StatusType] = armorStatusTypes

  override def getAttackElementType: ElementType = attackElementType
}

object DefaultMonster {
  private val monsterIdSupplier = new DefaultIdSupplier
}

case class DefaultHunter(name: String, inventory: Inventory) extends DefaultGameUnit(name) with Hunter {

  def this(name: String) {
    this(name, new DefaultInventory)
  }

  def getInventory: Inventory = inventory

  def getLife: Int = DefaultHunter.config.getHunterLifeMax

  def getArmor: Int = getInventory.getArmorProvided

  def getDamage: Int = getInventory.getDamageProvided

  def getAttackElementType: ElementType = getInventory.getAttackElementType

  def getArmorElementTypes: Seq[ElementType] = getInventory.getArmorElementTypes

  def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  def getArmorStatusTypes: Seq[StatusType] = getInventory.getArmorStatusTypes
}

object DefaultHunter {
  private final val config = ConfigLoader.loadGameConfig
}
