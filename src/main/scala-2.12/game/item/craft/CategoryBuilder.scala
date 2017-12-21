package game.item.craft

import game.config.{DefaultGameConfig, GameConfig}
import game.item._
import game.item.craft.bonus.{DAMAGE, PROTECTION}
import game.item.craft.nature.{NatureType, WEAPON}
import game.util.Procedural

/**
  * Created by nol on 29/11/17.
  */
class CategoryBuilder(gameConfig: GameConfig, itemTypeFactory: AbstractItemTypeFactory) {

  def this() {
    this(DefaultGameConfig.getGameConfig, DefaultItemTypeFactory.getDefaultItemFactory)
  }

  private def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  private def getRandomValue(level: Int, base: Int): Int = {
    Procedural.getRandomValue(level, base, gameConfig.getHunterStatsGrowth, gameConfig.getPercentageVariation)
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

  def createItemType(level: Int): ItemType = {
    var itemType = getNature.createItemType(level)
    for (addOn <- getAddOns) {
      addOn match {
        case CharmSlotAddOn => itemType = CharmSlot(itemType, getRandomSlot)
        case ElementAddOn(e) => itemType = Element(itemType, e)
        case StatusAddOn(s) => itemType = Status(itemType, s)
        case BonusAddOn(DAMAGE) => itemType = Damage(itemType, getRandomValue(level, gameConfig.getDamageBonusBase))
        case BonusAddOn(PROTECTION) => itemType = Protection(itemType, getRandomValue(level, gameConfig.getArmorBonusBase))
      }
    }
    itemType
  }
}
