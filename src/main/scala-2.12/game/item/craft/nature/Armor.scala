package game.item.craft.nature

import game.item.ArmorPart.ArmorPart
import game.item._

/**
  * Created by nol on 21/12/17.
  */

case class Armor[TItemType <: ItemType](armorPart: ArmorPart, decorator: AbstractDecorator[TItemType], itemTypeFactory: AbstractItemTypeFactory[TItemType]) extends DefaultNatureType[TItemType] {
  override val name: String = armorPart match {
    case ArmorPart.HEAD => "helmet"
    case ArmorPart.BODY => "plastron"
    case ArmorPart.ARMS => "arms"
    case ArmorPart.LEGS => "legs"
  }

  override def create(level: Int): TItemType = {
    decorator.decorateWithEquipment(
      decorator.decorateWithProtection(
        itemTypeFactory.createItemType(level), getRandomValue(level, gameConfig.getArmorBase)
      ),
      ARMOR_SLOT(armorPart)
    )
  }
}
