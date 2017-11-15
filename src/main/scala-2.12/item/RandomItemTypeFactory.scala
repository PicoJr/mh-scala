package item

import item.ArmorPart.ArmorPart
import item.Classification.Classification

import scala.util.Random

/**
  * Created by nol on 14/11/17.
  */
object RandomItemTypeFactory {

  def createWeaponType(level: Int, classifications: Classification*): ItemType = {
    val name = "w"
    val damage = 100
    // TODO random(level)
    val w = ItemType.createWeapon(name, level, damage)
    decorateItemRandomly(level, w, classifications: _*)
  }

  def createArmorType(level: Int, armorPart: ArmorPart, classifications: Classification*): ItemType = {
    val name = "a"
    val armor = 100
    // TODO random(level)
    val a = ItemType.createArmor(name, level, armor, armorPart)
    decorateItemRandomly(level, a, classifications: _*)
  }

  def createCharmType(level: Int, classifications: Classification*): ItemType = {
    val name = "c"
    val slots = Random.nextInt(3) + 1
    // 1,2,3
    val c = ItemType.createCharm(name, level, slots)
    decorateItemRandomly(level, c, classifications: _*)
  }

  def createMaterialType(level: Int): ItemType = {
    val name = "m"
    ItemType.createMaterial(name, level)
  }


  private def decorateItemRandomly(level: Int, item: ItemType, classifications: Classification*): ItemType = {
    var randomlyDecorated: ItemType = item
    val randomClassifications = Classification.takeRandom(classifications: _*)
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
