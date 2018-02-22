package game.item.craft.nature

import game.item.ArmorPart.ArmorPart
import game.item._

/**
  * Created by nol on 21/12/17.
  */

case class Armor(armorPart: ArmorPart, decorator: AbstractDecorator = DefaultDecorator.getInstance, itemTypeFactory: AbstractItemTypeFactory = DefaultItemTypeFactory.getInstance) extends DefaultNatureType {
  override val name: String = armorPart match {
    case ArmorPart.HEAD => "helmet"
    case ArmorPart.BODY => "plastron"
    case ArmorPart.ARMS => "arms"
    case ArmorPart.LEGS => "legs"
  }

  override def create(level: Int): ItemType = {
    decorator.decorateWithEquipment(
      decorator.decorateWithProtection(
        itemTypeFactory.createItemType(level), getRandomValue(level, gameConfig.getArmorBase)
      ),
      ARMOR_SLOT(armorPart)
    )
  }
}
