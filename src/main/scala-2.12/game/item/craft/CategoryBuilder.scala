package game.item.craft

import game.config.ConfigLoader
import game.item._
import game.util.Procedural

/**
  * Created by nol on 29/11/17.
  */
class CategoryBuilder {

  private def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  private def getRandomValue(level: Int, base: Int): Int = {
    Procedural.getRandomValue(level, base, CategoryBuilder.hunterConfig.getHunterStatsGrowth, CategoryBuilder.config.getPercentageVariation)
  }

  private var natureType: NatureType = WEAPON
  private var addOns: Seq[AddOn] = Seq.empty

  def getNature: NatureType = natureType

  def getAddOns: Seq[AddOn] = addOns

  def withNature(natureType: NatureType): CategoryBuilder = {
    this.natureType = natureType
    this
  }

  def withAddOn(addOn: AddOn): CategoryBuilder = {
    addOns = addOns :+ addOn
    this
  }

  def withAddOns(addOns: AddOn*): CategoryBuilder = {
    this.addOns = this.addOns ++ Seq(addOns: _*)
    this
  }

  def copy: CategoryBuilder = {
    (new CategoryBuilder).withNature(natureType).withAddOns(addOns: _*)
  }

  def createDescription: DescriptionBuilder = {
    val descriptionBuilder = new DescriptionBuilder()
    descriptionBuilder.addNature(getNature.name)
    for (addOn <- getAddOns) {
      addOn match {
        case CharmSlotAddOn => // descriptionBuilder.addAdjective("charmed") // removed for brevity
        case ElementAddOn(e) => descriptionBuilder.addAdjective(e.name)
        case StatusAddOn(s) => descriptionBuilder.addAdjective(s.name)
        case BonusAddOn(b) => descriptionBuilder.addAdjective(b.name)
      }
    }
    descriptionBuilder
  }

  def createItemType(level: Int): DefaultItemType = {
    var itemType = getNature match {
      case WEAPON => DefaultItemType.createWeapon(level, getRandomValue(level, CategoryBuilder.itemConfig.getDamageBase))
      case CHARM => DefaultItemType.createCharm(level, getRandomSlot)
      case ARMOR(armorPart) => DefaultItemType.createArmor(level, getRandomValue(level, CategoryBuilder.itemConfig.getArmorBase), armorPart)
    }
    for (addOn <- getAddOns) {
      addOn match {
        case CharmSlotAddOn => itemType = CharmSlot(itemType, getRandomSlot)
        case ElementAddOn(e) => itemType = Element(itemType, e)
        case StatusAddOn(s) => itemType = Status(itemType, s)
        case BonusAddOn(DAMAGE) => itemType = Damage(itemType, getRandomValue(level, CategoryBuilder.itemConfig.getDamageBonusBase))
        case BonusAddOn(PROTECTION) => itemType = Protection(itemType, getRandomValue(level, CategoryBuilder.itemConfig.getArmorBonusBase))
      }
    }
    itemType
  }
}

object CategoryBuilder {
  private final val config = ConfigLoader.loadGameConfig
  private final val itemConfig = ConfigLoader.loadItemConfig
  private final val hunterConfig = ConfigLoader.loadHunterConfig
}
