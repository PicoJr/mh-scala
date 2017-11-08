package item

import item.Classification.Classification
import item.ElementType.ElementType
import item.StatusType.StatusType

/**
  * Created by nol on 04/11/17.
  */
sealed class Item(name: String) {
  private final val uniqueID = ItemFactory.getNewUniqueItemID

  def getName: String = name

  def getUniqueID: Long = uniqueID

  def getClassifications: Set[Classification] = Set.empty

  def isClassifiedAs(classifications: Classification*): Boolean = {
    classifications.foldLeft(true)((found, c) => found & getClassifications.contains(c))
  }
}

class ItemDecorator(i: Item, c: Classification*) extends Item(i.getName) {
  val item: Item = i
  private final val uniqueDecoratorID = ItemFactory.getNewUniqueItemID

  override def getUniqueID: Long = uniqueDecoratorID

  override def getClassifications: Set[Classification] = i.getClassifications ++ c
}

case class Damage(wrapped: Item, damage: Int) extends ItemDecorator(wrapped, Classification.DAMAGE) {
  def getRawDamage: Int = damage
}

case class Status(wrapped: Item, statusType: StatusType) extends ItemDecorator(wrapped, Classification.STATUS) {
  def getStatusType: StatusType = statusType
}

case class Protection(wrapped: Item, armor: Int) extends ItemDecorator(wrapped, Classification.PROTECTION) {
  def getArmor: Int = armor
}

case class Equipment(wrapped: Item) extends ItemDecorator(wrapped, Classification.EQUIPMENT) {
  var equipped: Boolean = false

  def unEquip(): Unit = {
    equipped = false
  }

  def equip(): Unit = {
    equipped = true
  }

  def isEquipped: Boolean = equipped
}

case class Element(wrapped: Item, elementType: ElementType) extends ItemDecorator(wrapped, Classification.ELEMENT) {
  def getElementType: ElementType = elementType
}

case class CharmSlot(wrapped: Item, charmSlot: Int) extends ItemDecorator(wrapped, Classification.CHARM_SLOT) {
  def getCharmSlots: Int = charmSlot
}
