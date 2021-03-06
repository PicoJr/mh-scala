package game.item.craft.nature

import game.item._

/**
  * Created by nol on 21/12/17.
  */
case class Weapon(decorator: AbstractDecorator = DefaultDecorator.getInstance, itemTypeFactory: AbstractItemTypeFactory = DefaultItemTypeFactory.getInstance) extends DefaultNatureType {
  override val name = "sword"

  override def create(level: Int): ItemType = {
    decorator.decorateWithEquipment(
      decorator.decorateWithDamage(
        itemTypeFactory.createItemType(level), getRandomValue(level, gameConfig.getDamageBase)
      ),
      WEAPON_SLOT
    )
  }
}

