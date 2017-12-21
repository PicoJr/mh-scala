package game.item.craft.nature

import game.item.ItemType

/**
  * Created by nol on 21/12/17.
  */
object WEAPON extends DefaultNatureType {
  override val name = "sword"

  override def createItemType(level: Int): ItemType = {
    itemTypeFactory.createWeapon(level, getRandomValue(level, gameConfig.getDamageBase))
  }
}

