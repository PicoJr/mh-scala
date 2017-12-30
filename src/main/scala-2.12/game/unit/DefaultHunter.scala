package game.unit

import game.config.DefaultGameConfig
import game.item.Item
import game.item.element.ElementType
import game.item.inventory.Inventory
import game.item.status.StatusType

/**
  * Created by nol on 06/12/17.
  */
case class DefaultHunter[TItem <: Item](hunterName: String, hunterInventory: Inventory[TItem], hunterLife: Int = DefaultGameConfig.getGameConfig.getHunterLifeMax) extends Hunter[TItem] {

  override val inventory: Inventory[TItem] = hunterInventory

  override var name: String = hunterName

  override def getLife: Int = hunterLife

  override def getArmor: Int = inventory.getArmorProvided

  override def getDamage: Int = inventory.getDamageProvided

  override def getAttackElementType: ElementType = inventory.getAttackElementType

  override def getElementalResistances: Seq[ElementType] = inventory.getArmorElementTypes ++ inventory.getCharmsElementTypes

  override def getAttackStatusType: StatusType = inventory.getAttackStatusType

  override def getStatusResistances: Seq[StatusType] = inventory.getArmorStatusTypes ++ inventory.getCharmsStatusTypes
}
