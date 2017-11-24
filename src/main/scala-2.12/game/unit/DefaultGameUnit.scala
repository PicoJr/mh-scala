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

  override def getName: String = _name

  override def setName(newName: String): Unit = {
    _name = newName
  }

}

case class DefaultMonster(name: String, life: Int, armor: Int, damage: Int, attackStatusType: StatusType, attackElementType: ElementType, armorStatusTypes: Seq[StatusType], armorElementTypes: Seq[ElementType]) extends DefaultGameUnit(name) with Monster {
  private final val uniqueID = DefaultMonster.monsterIdSupplier.getNextUniqueId

  override def getUniqueId: Long = uniqueID

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

  override def getInventory: Inventory = inventory

  override def getLife: Int = DefaultHunter.config.getHunterLifeMax

  override def getArmor: Int = getInventory.getArmorProvided

  override def getDamage: Int = getInventory.getDamageProvided

  override def getAttackElementType: ElementType = getInventory.getAttackElementType

  override def getArmorElementTypes: Seq[ElementType] = getInventory.getArmorElementTypes

  override def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  override def getArmorStatusTypes: Seq[StatusType] = getInventory.getArmorStatusTypes
}

object DefaultHunter {
  private final val config = ConfigLoader.loadGameConfig
}
