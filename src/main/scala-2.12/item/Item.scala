package item

import item.ArmorPart.ArmorPart
import item.Classification.Classification
import item.ElementType.ElementType
import item.StatusType.StatusType

import scala.util.Random

/**
  * Created by nol on 04/11/17.
  */
sealed class Item(name: String) {
  private final val uniqueID = Item.getNewUniqueItemID

  def getName: String = name

  def getUniqueID: Long = uniqueID

  def getRawDamage: Int = 0

  /**
    *
    * @return StatusType, default: NONE
    */
  def getStatusType: StatusType = StatusType.NONE

  def getArmor: Int = 0

  def equip(): Unit = {}

  def unEquip(): Unit = {}

  def isEquipped: Boolean = false

  def getSlotTypeRequirement: SlotTypeRequirements = INVENTORY_SLOT()

  def getClassifications: Set[Classification] = Set.empty

  def isClassifiedAs(classifications: Classification*): Boolean = {
    classifications.foldLeft(true)((found, c) => found & getClassifications.contains(c))
  }

  /**
    *
    * @return ElementType, default : NONE
    */
  def getElementType: ElementType = ElementType.NONE

  def getCharmSlotsProvided: Int = 0
}

object Item {

  private var itemID: Long = 0

  def createWeapon(name: String, damage: Int): Item = {
    Damage(Equipment(new Item(name), WEAPON_SLOT()), damage)
  }

  def createArmor(name: String, armor: Int, armorPart: ArmorPart): Item = {
    Protection(Equipment(new Item(name), ARMOR_SLOT(armorPart)), armor)
  }

  def createCharm(name: String, slotsRequired: Int): Item = {
    Equipment(new Item(name), CHARM_SLOT(slotsRequired))
  }

  def getNewUniqueItemID: Long = {
    itemID += 1
    itemID
  }

  def isWeapon(i: Item): Boolean = {
    getSlotTypeRequirement(i) match {
      case WEAPON_SLOT() => true
      case _ => false
    }
  }

  def isEquipment(i: Item): Boolean = {
    i.isClassifiedAs(Classification.EQUIPMENT)
  }

  def isArmor(i: Item): Boolean = {
    getSlotTypeRequirement(i) match {
      case ARMOR_SLOT(_) => true
      case _ => false
    }
  }

  def isCharm(i: Item): Boolean = {
    getSlotTypeRequirement(i) match {
      case CHARM_SLOT(_) => true
      case _ => false
    }
  }

  def isArmorPart(i: Item, armorPart: ArmorPart): Boolean = {
    getSlotTypeRequirement(i) match {
      case ARMOR_SLOT(part) if part == armorPart => true
      case _ => false
    }
  }

  def getRawDamage(i: Item): Int = {
    i.getRawDamage
  }

  def getArmor(i: Item): Int = {
    i.getArmor
  }

  def equip(i: Item): Unit = {
    i.equip()
  }

  def unEquip(i: Item): Unit = {
    i.unEquip()
  }

  def isEquipped(i: Item): Boolean = {
    i.isEquipped
  }

  def getElementType(i: Item): ElementType = {
    i.getElementType
  }

  def getStatusType(i: Item): StatusType = {
    i.getStatusType
  }

  def getCharmSlotsProvided(i: Item): Int = {
    i.getCharmSlotsProvided
  }

  def getSlotTypeRequirement(i: Item): SlotTypeRequirements = {
    i.getSlotTypeRequirement
  }

  def getCharmSlotsRequired(i: Item): Int = {
    getSlotTypeRequirement(i) match {
      case CHARM_SLOT(slot) => slot
      case _ => 0
    }
  }
}

/**
  * Note: if an item is decorated twice by the same decorator class,
  * then the last decorator takes precedence over the first one.
  *
  * @param i wrapped
  * @param c classifications obtained with decorator
  */
abstract class ItemDecorator(i: Item, c: Classification*) extends Item(i.getName) {
  val item: Item = i
  private final val uniqueDecoratorID = Item.getNewUniqueItemID

  /**
    * Note: item.getUniqueID != decorator(item).getUniqueID
    *
    * @return unique ID
    */
  override def getUniqueID: Long = uniqueDecoratorID

  override def getClassifications: Set[Classification] = i.getClassifications ++ c

  override def getRawDamage: Int = i.getRawDamage

  override def getStatusType: StatusType = i.getStatusType

  override def getArmor: Int = i.getArmor

  override def equip(): Unit = i.equip()

  override def unEquip(): Unit = i.unEquip()

  override def isEquipped: Boolean = i.isEquipped

  override def getSlotTypeRequirement: SlotTypeRequirements = i.getSlotTypeRequirement

  override def getElementType: ElementType = i.getElementType

  override def getCharmSlotsProvided: Int = i.getCharmSlotsProvided
}

case class Damage(wrapped: Item, damage: Int) extends ItemDecorator(wrapped, Classification.DAMAGE) {
  override def getRawDamage: Int = damage
}

case class Status(wrapped: Item, statusType: StatusType) extends ItemDecorator(wrapped, Classification.STATUS) {
  override def getStatusType: StatusType = statusType
}

case class Protection(wrapped: Item, armor: Int) extends ItemDecorator(wrapped, Classification.PROTECTION) {
  override def getArmor: Int = armor
}

case class Equipment(wrapped: Item, slotTypeRequirement: SlotTypeRequirements) extends ItemDecorator(wrapped, Classification.EQUIPMENT) {
  var equipped: Boolean = false

  /**
    * post: item.isEquipped false
    */
  override def unEquip(): Unit = {
    equipped = false
  }

  /**
    * post: item.isEquipped true
    */
  override def equip(): Unit = {
    equipped = true
  }

  override def isEquipped: Boolean = equipped

  override def getSlotTypeRequirement: SlotTypeRequirements = slotTypeRequirement
}

case class Element(wrapped: Item, elementType: ElementType) extends ItemDecorator(wrapped, Classification.ELEMENT) {
  override def getElementType: ElementType = elementType
}

/**
  *
  * @param wrapped            item
  * @param charmSlotsProvided >= 1
  */
case class CharmSlot(wrapped: Item, charmSlotsProvided: Int) extends ItemDecorator(wrapped, Classification.CHARM_SLOT) {
  override def getCharmSlotsProvided: Int = charmSlotsProvided
}

object Classification extends Enumeration {
  type Classification = Value
  val CHARM_SLOT, DAMAGE, ELEMENT, EQUIPMENT, PROTECTION, STATUS = Value

  def getRandomClassification: Classification = {
    Classification.EQUIPMENT // TODO make it random
  }

  /**
    *
    * @param classifications to choose from
    * @param n               <= 0 -> empty set, n >= maxId -> all classifications
    * @return min(n, classifications.maxId) distinct classifications
    */
  def takeRandom(n: Int, classifications: Classification*): Set[Classification] = {
    if (n <= 0) {
      Set.empty
    } else {
      Random.shuffle(Set(classifications: _*)).take(n)
    }
  }

}

object RandomItemFactory {
  /**
    *
    * @param level of weapon
    * @return item s.t. Item.isWeapon(item)
    */
  def getRandomDefaultWeapon(level: Int): Item = {
    val name = "weapon" + Random.nextInt()
    val damage = 100 // TODO make it random(level)
    Item.createWeapon(name, damage)
  }

  def getRandomWeapon(level: Int): Item = {
    val w = getRandomDefaultWeapon(level)
    decorateItemRandomly(level, w, Classification.CHARM_SLOT, Classification.ELEMENT, Classification.PROTECTION, Classification.STATUS)
  }

  /**
    *
    * @param level of armor
    * @return item s.t. Item.isArmor(item)
    */
  def getRandomDefaultArmor(level: Int): Item = {
    val name = "armor" + Random.nextInt()
    val armor = 100
    // TODO make it random(level)
    val armorPart = ArmorPart.getRandomArmorPart
    Item.createArmor(name, armor, armorPart)
  }

  def getRandomArmor(level: Int): Item = {
    val a = getRandomDefaultArmor(level)
    decorateItemRandomly(level, a, Classification.CHARM_SLOT, Classification.DAMAGE, Classification.ELEMENT, Classification.STATUS)
  }

  /**
    *
    * @param level of charm
    * @return item s.t. Item.isCharm(item)
    */
  def getRandomDefaultCharm(level: Int): Item = {
    val name = "charm" + Random.nextInt()
    val slotsRequired = Random.nextInt(3) + 1 // 1,2,3
    Item.createCharm(name, slotsRequired)
  }

  def getRandomCharm(level: Int): Item = {
    val c = getRandomDefaultCharm(level)
    decorateItemRandomly(level, c, Classification.DAMAGE, Classification.ELEMENT, Classification.PROTECTION, Classification.STATUS)
  }

  private def decorateItemRandomly(level: Int, item: Item, classifications: Classification*): Item = {
    var randomlyDecorated: Item = item
    val n = classifications.size
    // TODO random(level), increasing, <= classifications.size
    val randomClassifications = Classification.takeRandom(n, classifications: _*)
    for (c <- randomClassifications) {
      c match {
        case Classification.CHARM_SLOT =>
          val charmSlotsProvided = Random.nextInt(3) + 1 // 1,2,3
          randomlyDecorated = CharmSlot(randomlyDecorated, charmSlotsProvided)
        case Classification.DAMAGE =>
          val damage = 10 // TODO random(level)
          randomlyDecorated = Damage(randomlyDecorated, damage)
        case Classification.ELEMENT =>
          val element = ElementType.getRandomElementType
          randomlyDecorated = Element(randomlyDecorated, element)
        case Classification.PROTECTION =>
          val armor = 10 // TODO random(level)
          randomlyDecorated = Protection(randomlyDecorated, armor)
        case Classification.STATUS =>
          val statusType = StatusType.getRandomStatusType
          randomlyDecorated = Status(randomlyDecorated, statusType)
        case _ =>
      }
    }
    randomlyDecorated
  }
}