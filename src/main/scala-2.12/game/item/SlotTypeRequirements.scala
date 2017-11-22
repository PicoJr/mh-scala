package game.item

import game.item.ArmorPart.ArmorPart

import scala.util.Random

/** type of slot required for an item in order to be equipped
  * Created by nol on 09/11/17.
  */
sealed abstract class SlotTypeRequirements

case object WEAPON_SLOT extends SlotTypeRequirements

/** Charm slot requirement
  *
  * @param slots number of charm slots required >= 1
  */
case class CHARM_SLOT(slots: Int) extends SlotTypeRequirements

/** Inventory slot requirement (implicitly 1 slot)
  *
  */
case object MATERIAL_SLOT extends SlotTypeRequirements

/** Armor slot requirement (implicitely 1 slot) of armor part.
  *
  * @param part of armor
  */
case class ARMOR_SLOT(part: ArmorPart) extends SlotTypeRequirements

object ArmorPart extends Enumeration {
  type ArmorPart = Value
  val HEAD, BODY, ARMS, LEGS = Value

  def getRandomArmorPart: ArmorPart = {
    ArmorPart(Random.nextInt(ArmorPart.maxId))
  }
}