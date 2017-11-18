package game.item

import game.config.ConfigLoader
import game.item.ArmorPart.ArmorPart
import game.item.Classification.Classification
import game.util.Procedural

/** Factory for procedural item type generation
  * Created by nol on 14/11/17.
  */
object RandomItemTypeFactory {

  private val config = ConfigLoader.loadConfig

  private def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  private def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, config.getStatsGrowth, config.getPercentageVariation)

  def createWeaponType(level: Int, classifications: Classification*): ItemType = {
    val w = ItemType.createWeapon("weapon", level, getRandomValue(level, config.getDamageBase))
    val weapon = decorateItemRandomly(level, w, classifications: _*)
    weapon.setName(NameFactory.getRandomWeaponDescription(weapon).getDescription)
    weapon
  }

  def createArmorType(level: Int, armorPart: ArmorPart, classifications: Classification*): ItemType = {
    val a = ItemType.createArmor("armor", level, getRandomValue(level, config.getArmorBase), armorPart)
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
    val material = ItemType.createMaterial("material", level)
    material.setName(NameFactory.getRandomMaterialDescription(material).getDescription)
    material
  }

  private def decorateItemRandomly(level: Int, item: ItemType, classifications: Classification*): ItemType = {
    var randomlyDecorated: ItemType = item
    for (c <- classifications) {
      c match {
        case Classification.CHARM_SLOT =>
          val charmSlotsProvided = getRandomSlot
          randomlyDecorated = CharmSlot(randomlyDecorated, charmSlotsProvided)
        case Classification.DAMAGE =>
          val damage = getRandomValue(level, config.getDamageBonusBase)
          randomlyDecorated = Damage(randomlyDecorated, damage)
        case Classification.ELEMENT =>
          val element = ElementType.getRandomElementType
          randomlyDecorated = Element(randomlyDecorated, element)
        case Classification.PROTECTION =>
          val armor = getRandomValue(level, config.getArmorBonusBase)
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
