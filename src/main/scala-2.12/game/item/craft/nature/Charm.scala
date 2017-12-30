package game.item.craft.nature

import game.item.{AbstractItemTypeFactory, ItemType}

/**
  * Created by nol on 21/12/17.
  */
case class Charm[TItemType <: ItemType](itemTypeFactory: AbstractItemTypeFactory[TItemType]) extends DefaultNatureType[TItemType] {
  override val name = "charm"

  override def create(level: Int): TItemType = {
    itemTypeFactory.createCharm(level, getRandomSlot)
  }
}

