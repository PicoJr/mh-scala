package game.item.craft.nature

import game.item.ArmorPart.ArmorPart
import game.item.{ArmorPart, ItemType}

/**
  * Created by nol on 21/12/17.
  */

class ARMOR(armorPart: ArmorPart) extends DefaultNatureType {
  override val name: String = armorPart match {
    case ArmorPart.HEAD => "helmet"
    case ArmorPart.BODY => "plastron"
    case ArmorPart.ARMS => "arms"
    case ArmorPart.LEGS => "legs"
  }

  override def createItemType(level: Int): ItemType = {
    itemTypeFactory.createArmor(level, getRandomValue(level, gameConfig.getArmorBase), armorPart)
  }
}
