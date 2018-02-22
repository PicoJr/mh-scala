package game.item.craft.nature

import game.item._
import game.util.Procedural

/**
  * Created by nol on 21/12/17.
  */
case class Charm(decorator: AbstractDecorator = DefaultDecorator.getInstance, itemTypeFactory: AbstractItemTypeFactory = DefaultItemTypeFactory.getInstance) extends DefaultNatureType {
  override val name = "charm"

  override def create(level: Int): ItemType = {
    decorator.decorateWithEquipment(
      itemTypeFactory.createItemType(level),
      CHARM_SLOT(Procedural.pickRandom(1, 2, 3).get)
    )
  }
}

