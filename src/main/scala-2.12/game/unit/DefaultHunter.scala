package game.unit

import game.config.{DefaultGameConfig, GameConfig}
import game.item.element.ElementType
import game.item.inventory.{DefaultInventory, Inventory}
import game.item.status.StatusType

/**
  * Created by nol on 06/12/17.
  */
case class DefaultHunter(name: String, hunterInventory: Inventory = new DefaultInventory, gameConfig: GameConfig = DefaultGameConfig.getGameConfig) extends DefaultGameUnit(name) with Hunter {

  def this() {
    this(DefaultGameConfig.getGameConfig.getHunterName)
  }

  val inventory: Inventory = hunterInventory

  override def getLife: Int = gameConfig.getHunterLifeMax

  override def getArmor: Int = inventory.getArmorProvided

  override def getDamage: Int = inventory.getDamageProvided

  override def getAttackElementType: ElementType = inventory.getAttackElementType

  override def getElementalResistances: Seq[ElementType] = inventory.getArmorElementTypes ++ inventory.getCharmsElementTypes

  override def getAttackStatusType: StatusType = inventory.getAttackStatusType

  override def getStatusResistances: Seq[StatusType] = inventory.getArmorStatusTypes ++ inventory.getCharmsStatusTypes
}
