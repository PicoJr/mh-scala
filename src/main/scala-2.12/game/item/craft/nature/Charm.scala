package game.item.craft.nature

import game.item.{AbstractDecorator, AbstractItemTypeFactory, CHARM_SLOT, ItemType}
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
case class Charm(decorator: AbstractDecorator, itemTypeFactory: AbstractItemTypeFactory) extends DefaultNatureType {
  override val name = "charm"

  override def create(level: Int): ItemType = {
    decorator.decorateWithEquipment(
      itemTypeFactory.createItemType(level),
      CHARM_SLOT(Procedural.pickRandom(1, 2, 3).get)
    )
  }
}

