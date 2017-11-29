package game.item.craft

import game.config.ConfigLoader
import game.item._
import game.item.element.ElementType
import game.util.Procedural

/**
  * Created by nol on 20/11/17.
  */
object CraftPrototype {

  private val config = ConfigLoader.loadGameConfig

  private def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  private def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, config.getHunterStatsGrowth, config.getPercentageVariation)


  class CategoryBuilder {
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
  }

  def createItemType(categoryBuilder: CategoryBuilder, level: Int): DefaultItemType = {
    var itemType = categoryBuilder.getNature match {
      case WEAPON => DefaultItemType.createWeapon(level, getRandomValue(level, config.getDamageBase))
      case CHARM => DefaultItemType.createCharm(level, getRandomSlot)
      case ARMOR(armorPart) => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), armorPart)
    }
    for (addOn <- categoryBuilder.getAddOns) {
      addOn match {
        case ElementAddOn(e) => itemType = Element(itemType, e)
        case StatusAddOn(s) => itemType = Status(itemType, s)
        case BonusAddOn(DAMAGE) => itemType = Damage(itemType, getRandomValue(level, config.getDamageBonusBase))
        case BonusAddOn(PROTECTION) => itemType = Protection(itemType, getRandomValue(level, config.getArmorBonusBase))
      }
    }
    itemType
  }

  def createDescription(categoryBuilder: CategoryBuilder): DescriptionBuilder = {
    val descriptionBuilder = new DescriptionBuilder()
    descriptionBuilder.addNature(categoryBuilder.getNature.name)
    for (addOn <- categoryBuilder.getAddOns) {
      addOn match {
        case ElementAddOn(e) => descriptionBuilder.addAdjective(e.name)
        case StatusAddOn(s) => descriptionBuilder.addAdjective(s.name)
        case BonusAddOn(b) => descriptionBuilder.addAdjective(b.name)
      }
    }
    descriptionBuilder
  }

  case class CraftStep(itemTypeRoot: ItemType, categoryRoot: CategoryBuilder, crafts: Crafts, materialPool: MaterialPool)

  class MaterialPool {
    private var materials = Map.empty[AddOn, ItemType]

    def getMaterial(addOn: AddOn, level: Int): ItemType = {
      materials.get(addOn) match {
        case Some(m) => m
        case None =>
          val m = createMaterialFromAddOn(addOn, level)
          materials += (addOn -> m)
          m
      }
    }

    private def createMaterialFromAddOn(addOn: AddOn, level: Int): ItemType = {
      val descriptionBuilder = new DescriptionBuilder().addNature("material")
      addOn match {
        case ElementAddOn(e) => descriptionBuilder.addElement(e.name)
        case StatusAddOn(s) => descriptionBuilder.addAdjective(s.name)
        case BonusAddOn(b) => descriptionBuilder.addAdjective(b.name)
      }
      DefaultItemType.createMaterial(descriptionBuilder.getDescription, level)
    }

  }

  def craftItemType(craftStep: CraftStep): Unit = {
    def craftWithAddOn(craftStep: CraftStep, addOn: AddOn): Unit = {
      val resultCategory = craftStep.categoryRoot.copy.withAddOn(addOn)
      val result = createItemType(resultCategory, craftStep.itemTypeRoot.getLevel + 1)
      val resultDescription = createDescription(resultCategory)
      result.setName(resultDescription.getDescription)
      val material = craftStep.materialPool.getMaterial(addOn, craftStep.itemTypeRoot.getLevel)
      craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
      craftItemType(CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
    }

    if (craftStep.itemTypeRoot.getLevel == config.getLevelMin) {
      for (element <- ElementType.values) {
        craftWithAddOn(craftStep, ElementAddOn(element))
      }
    } else if (craftStep.itemTypeRoot.getLevel == config.getLevelMin + 1) {
      for (status <- StatusType.values) {
        craftWithAddOn(craftStep, StatusAddOn(status))
      }
    } else if (craftStep.itemTypeRoot.getLevel == config.getLevelMin + 2) {
      for (bonus <- BonusType.values) {
        craftWithAddOn(craftStep, BonusAddOn(bonus))
      }
    }
  }

  def generateCraft: Crafts = {
    val crafts = new DefaultCrafts
    val materialPool = new MaterialPool
    for (natureCategory <- NatureType.values) {
      val categoryRoot = (new CategoryBuilder).withNature(natureCategory)
      val itemTypeRoot = createItemType(categoryRoot, config.getLevelMin)
      itemTypeRoot.setName(createDescription(categoryRoot).getDescription)
      craftItemType(CraftStep(itemTypeRoot, categoryRoot, crafts, materialPool))
    }
    crafts
  }
}
