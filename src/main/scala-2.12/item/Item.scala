package item

import item.ElementType.ElementType
import StatusType.StatusType

/**
  * Created by nol on 04/11/17.
  */
sealed trait Item {
  def getName: String

  def getQuantity: Int = 1
}

trait Damage extends Item {
  def getRawDamage: Int
}

trait Status extends Item {
  def getStatusType: StatusType
}

trait Protection extends Item {
  def getArmor: Int
}

trait Equipment extends Item {
  var equipped: Boolean = false

  def unEquip(): Unit = {
    equipped = false
  }

  def equip(): Unit = {
    equipped = true
  }

  def isEquipped: Boolean = equipped
}

trait Element extends Item {
  def getElementType: ElementType
}

trait CharmSlot extends Item {
  def getCharmSlots: Int
}

case class Weapon(name: String, damage: Int, charmSlots: Int) extends Item with Equipment with Damage with CharmSlot {

  override def getName: String = name

  override def getRawDamage: Int = damage

  override def getCharmSlots: Int = charmSlots
}

case class Armor(name: String, armor: Int, charmSlots: Int) extends Item with Equipment with Protection with CharmSlot {

  override def getName: String = name

  override def getArmor: Int = armor

  override def getCharmSlots: Int = charmSlots
}
