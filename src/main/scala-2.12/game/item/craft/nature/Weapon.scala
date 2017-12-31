package game.item.craft.nature

import game.item.{AbstractItemTypeFactory, ItemType}

/**
  * Created by nol on 21/12/17.
  */
case class Weapon[TItemType <: ItemType](itemTypeFactory: AbstractItemTypeFactory[TItemType]) extends DefaultNatureType[TItemType] {
  override val name = "sword"

  override def create(level: Int): TItemType = {
    itemTypeFactory.createWeapon(level, getRandomValue(level, gameConfig.getDamageBase))
  }
}

