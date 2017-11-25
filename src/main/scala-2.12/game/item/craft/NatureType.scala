package game.item.craft

import game.item.ArmorPart
import game.item.ArmorPart.ArmorPart

/**
  * Created by nol on 25/11/17.
  */
sealed trait NatureType {
  val name: String
}

object NatureType {
  final val values = Seq(
    WEAPON, ARMOR(ArmorPart.HEAD), ARMOR(ArmorPart.BODY),
    ARMOR(ArmorPart.ARMS), ARMOR(ArmorPart.LEGS), CHARM)
}

case object WEAPON extends NatureType {
  override val name = "sword"
}

case class ARMOR(armorPart: ArmorPart) extends NatureType {
  override val name: String = armorPart match {
    case ArmorPart.HEAD => "helmet"
    case ArmorPart.BODY => "plastron"
    case ArmorPart.ARMS => "arms"
    case ArmorPart.LEGS => "legs"
  }
}

case object CHARM extends NatureType {
  override val name = "charm"
}


