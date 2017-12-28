package game.unit

import game.config.DefaultGameConfig
import game.item.element.ElementType
import game.item.inventory.{DefaultInventory, Inventory}
import game.item.status.StatusType

/**
  * Created by nol on 06/12/17.
  */
case class DefaultHunter(hunterName: String, hunterInventory: Inventory = new DefaultInventory, hunterLife: Int = DefaultGameConfig.getGameConfig.getHunterLifeMax) extends Hunter {

  def this() {
    this(DefaultGameConfig.getGameConfig.getHunterName)
  }

  override val inventory: Inventory = hunterInventory

  override var name: String = hunterName

  override val life: Int = hunterLife

  override val armor: Int = inventory.getArmorProvided

  override val damage: Int = inventory.getDamageProvided

  override val attackElementType: ElementType = inventory.getAttackElementType

  override val elementalResistances: Seq[ElementType] = inventory.getArmorElementTypes ++ inventory.getCharmsElementTypes

  override val attackStatusType: StatusType = inventory.getAttackStatusType

  override val statusResistances: Seq[StatusType] = inventory.getArmorStatusTypes ++ inventory.getCharmsStatusTypes
}
