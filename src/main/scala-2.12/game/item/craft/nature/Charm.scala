package game.item.craft.nature

import game.item.{AbstractDecorator, AbstractItemTypeFactory, ItemType}
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
case class Charm[TItemType <: ItemType](decorator: AbstractDecorator[TItemType], itemTypeFactory: AbstractItemTypeFactory[TItemType]) extends DefaultNatureType[TItemType] {
  override val name = "charm"

  override def create(level: Int): TItemType = {
    decorator.decorateWithCharmSlot(itemTypeFactory.createItemType(level), Procedural.pickRandom(1, 2, 3).get)
  }
}

