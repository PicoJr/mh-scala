package game.item.craft.nature

import game.item.ArmorPart
import game.item.ArmorPart.ArmorPart

/**
  * Created by nol on 21/12/17.
  */

case class ARMOR(armorPart: ArmorPart) extends NatureType {
  override val name: String = armorPart match {
    case ArmorPart.HEAD => "helmet"
    case ArmorPart.BODY => "plastron"
    case ArmorPart.ARMS => "arms"
    case ArmorPart.LEGS => "legs"
  }
}
