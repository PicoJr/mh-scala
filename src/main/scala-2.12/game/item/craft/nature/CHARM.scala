package game.item.craft.nature

import game.item.ItemType

/**
  * Created by nol on 21/12/17.
  */
object CHARM extends DefaultNatureType {
  override val name = "charm"

  override def createItemType(level: Int): ItemType = {
    itemTypeFactory.createCharm(level, getRandomSlot)
  }
}

