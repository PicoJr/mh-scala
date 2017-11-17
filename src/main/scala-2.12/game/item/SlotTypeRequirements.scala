package game.item

import game.item.ArmorPart.ArmorPart

import scala.util.Random

/** type of slot required for an item in order to be equipped
  * Created by nol on 09/11/17.
  */
sealed abstract class SlotTypeRequirements

case class WEAPON_SLOT() extends SlotTypeRequirements

/**
  *
  * @param slots number of charm slots required, >= 1
  */
case class CHARM_SLOT(slots: Int) extends SlotTypeRequirements

case class INVENTORY_SLOT() extends SlotTypeRequirements

case class ARMOR_SLOT(part: ArmorPart) extends SlotTypeRequirements

object ArmorPart extends Enumeration {
  type ArmorPart = Value
  val HEAD, BODY, ARMS, LEGS = Value

  def getRandomArmorPart: ArmorPart = {
    ArmorPart(Random.nextInt(ArmorPart.maxId))
  }
}