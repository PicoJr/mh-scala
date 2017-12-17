package game.unit

import game.config.{DefaultGameConfig, GameConfig}
import game.item.element.ElementType
import game.item.inventory.{DefaultInventory, Inventory}
import game.item.status.StatusType

/**
  * Created by nol on 06/12/17.
  */
case class DefaultHunter(name: String, inventory: Inventory, gameConfig: GameConfig) extends DefaultGameUnit(name) with Hunter {

  def this(name: String) {
    this(name, new DefaultInventory, DefaultGameConfig.getGameConfig)
  }

  def this() {
    this(DefaultGameConfig.getGameConfig.getHunterName)
  }

  override def getInventory: Inventory = inventory

  override def getLife: Int = gameConfig.getHunterLifeMax

  override def getArmor: Int = getInventory.getArmorProvided

  override def getDamage: Int = getInventory.getDamageProvided

  override def getAttackElementType: ElementType = getInventory.getAttackElementType

  override def getElementalResistances: Seq[ElementType] = getInventory.getArmorElementTypes ++ getInventory.getCharmsElementTypes

  override def getAttackStatusType: StatusType = getInventory.getAttackStatusType

  override def getStatusResistances: Seq[StatusType] = getInventory.getArmorStatusTypes ++ getInventory.getCharmsStatusTypes
}
