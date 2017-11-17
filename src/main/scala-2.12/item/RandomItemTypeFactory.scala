package item

import config.Config
import item.ArmorPart.ArmorPart
import item.Classification.Classification

import scala.util.Random

/**
  * Created by nol on 14/11/17.
  */
object RandomItemTypeFactory {

  def getRandomSlot: Int = Random.nextInt(3) + 1 // 1,2,3

  def getVariation: Double = Random.nextDouble() * Config.PERCENTAGE_VARIATION / 100.0

  def getRandomValue(level: Int, base: Int): Int = {
    val value = base * Math.pow(Config.STATS_GROWTH, level)
    (value + (getVariation * value)).toInt
  }


  def createWeaponType(level: Int, classifications: Classification*): ItemType = {
    val w = ItemType.createWeapon("weapon", level, getRandomValue(level, Config.DAMAGE_BASE))
    val weapon = decorateItemRandomly(level, w, classifications: _*)
    weapon.setName(NameFactory.getRandomWeaponDescription(weapon).getDescription)
    weapon
  }

  def createArmorType(level: Int, armorPart: ArmorPart, classifications: Classification*): ItemType = {
    val a = ItemType.createArmor("armor", level, getRandomValue(level, Config.ARMOR_BASE), armorPart)
    val armor = decorateItemRandomly(level, a, classifications: _*)
    armor.setName(NameFactory.getRandomArmorDescription(armor).getDescription)
    armor
  }

  def createCharmType(level: Int, classifications: Classification*): ItemType = {
    val c = ItemType.createCharm("charm", level, getRandomSlot)
    val charm = decorateItemRandomly(level, c, classifications: _*)
    charm.setName(NameFactory.getRandomCharmDescription(charm).getDescription)
    charm
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
          val charmSlotsProvided = getRandomSlot
          randomlyDecorated = CharmSlot(randomlyDecorated, charmSlotsProvided)
        case Classification.DAMAGE =>
          val damage = getRandomValue(level, Config.DAMAGE_BONUS_BASE)
          randomlyDecorated = Damage(randomlyDecorated, damage)
        case Classification.ELEMENT =>
          val element = ElementType.getRandomElementType
          randomlyDecorated = Element(randomlyDecorated, element)
        case Classification.PROTECTION =>
          val armor = getRandomValue(level, Config.ARMOR_BONUS_BASE)
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
