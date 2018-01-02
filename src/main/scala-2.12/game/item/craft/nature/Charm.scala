package game.item.craft.nature

import game.item.{AbstractDecorator, AbstractItemTypeFactory, CHARM_SLOT, ItemType}
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
case class Charm[TItemType <: ItemType](decorator: AbstractDecorator[TItemType], itemTypeFactory: AbstractItemTypeFactory[TItemType]) extends DefaultNatureType[TItemType] {
  override val name = "charm"

  override def create(level: Int): TItemType = {
    decorator.decorateWithEquipment(
      itemTypeFactory.createItemType(level),
      CHARM_SLOT(Procedural.pickRandom(1, 2, 3).get)
    )
  }
}

