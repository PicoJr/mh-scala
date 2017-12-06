package game.unit

import game.config.ConfigLoader
import game.id.DefaultIdSupplier
import game.item.element.ElementType
import game.item.inventory.{DefaultInventory, Inventory}
import game.item.status.StatusType

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

  override def getElementalResistances: Seq[ElementType] = armorElementTypes

  override def getAttackStatusType: StatusType = attackStatusType

  override def getStatusResistances: Seq[StatusType] = armorStatusTypes

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

  override def getLife: Int = DefaultHunter.hunterConfig.getHunterLifeMax

  override def getArmor: Int = getInventory.getArmorProvided

  override def getDamage: Int = getInventory.getDamageProvided

  override def getAttackElementType: ElementType = getInventory.getAttackElementType

  override def getElementalResistances: Seq[ElementType] = getInventory.getArmorElementTypes ++ getInventory.getCharmsElementTypes

  override def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  override def getStatusResistances: Seq[StatusType] = getInventory.getArmorStatusTypes ++ getInventory.getCharmsStatusTypes
}

object DefaultHunter {
  private final val hunterConfig = ConfigLoader.loadHunterConfig
}
