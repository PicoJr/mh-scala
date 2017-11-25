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
    var natureType: NatureType = WEAPON
    var elementType: Option[ElementType] = Option.empty[ElementType]
    var statusType: Option[StatusType] = Option.empty[StatusType]
    var bonusType: Option[BonusType] = Option.empty[BonusType]

    def withNatureCategory(natureCategory: NatureType): CategoryBuilder = {
      this.natureType = natureCategory
      this
    }

    def withElementCategory(elementCategory: ElementType): CategoryBuilder = {
      this.elementType = Some(elementCategory)
      this
    }

    def withStatusCategory(statusCategory: StatusType): CategoryBuilder = {
      this.statusType = Some(statusCategory)
      this
    }

    def withBonusCategory(bonusCategory: BonusType): CategoryBuilder = {
      this.bonusType = Some(bonusCategory)
      this
    }

    def copy: CategoryBuilder = {
      val copy = new CategoryBuilder
      copy.natureType = natureType
      copy.elementType = elementType
      copy.bonusType = bonusType
      copy.statusType = statusType
      copy
    }
  }

  def createItemType(categoryBuilder: CategoryBuilder, level: Int): DefaultItemType = {
    var itemType = categoryBuilder.natureType match {
      case WEAPON => DefaultItemType.createWeapon(level, getRandomValue(level, config.getDamageBase))
      case CHARM => DefaultItemType.createCharm(level, getRandomSlot)
      case ARMOR(armorPart) => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), armorPart)
    }
    itemType = categoryBuilder.elementType match {
      case Some(e) => Element(itemType, e)
      case None => itemType
    }
    itemType = categoryBuilder.statusType match {
      case Some(s) => Status(itemType, s)
      case None => itemType
    }
    itemType = categoryBuilder.bonusType match {
      case Some(DAMAGE) => Damage(itemType, getRandomValue(level, config.getDamageBonusBase))
      case Some(PROTECTION) => Protection(itemType, getRandomValue(level, config.getArmorBonusBase))
      case _ => itemType
    }
    itemType
  }

  def createDescription(categoryBuilder: CategoryBuilder): DescriptionBuilder = {
    val descriptionBuilder = new DescriptionBuilder()
    descriptionBuilder.addNature(categoryBuilder.natureType.name)
    categoryBuilder.elementType match {
      case Some(e) => descriptionBuilder.addAdjective(e.name)
      case None =>
    }
    categoryBuilder.statusType match {
      case Some(s) => descriptionBuilder.addAdjective(s.name)
      case None =>
    }
    categoryBuilder.bonusType match {
      case Some(b) => descriptionBuilder.addAdjective(b.name)
      case None =>
    }
    descriptionBuilder
  }

  object MaterialFactory {

    def createMaterialFromElement(elementType: ElementType, level: Int): DefaultItemType = {
      val descriptionBuilder = new DescriptionBuilder().addNature("material").addElement(elementType.name)
      DefaultItemType.createMaterial(descriptionBuilder.getDescription, level)
    }

    def createMaterialFromStatus(statusType: StatusType, level: Int): DefaultItemType = {
      val descriptionBuilder = new DescriptionBuilder().addNature("material").addAdjective(statusType.name)
      DefaultItemType.createMaterial(descriptionBuilder.getDescription, level)
    }

    def createMaterialFromBonus(bonusType: BonusType, level: Int): DefaultItemType = {
      val descriptionBuilder = new DescriptionBuilder().addNature("material").addAdjective(bonusType.name)
      DefaultItemType.createMaterial(descriptionBuilder.getDescription, level)
    }

  }


  case class CraftStep(itemTypeRoot: ItemType, categoryRoot: CategoryBuilder, crafts: Crafts, materialPool: MaterialPool)

  class MaterialPool {
    private var elementMaterial = Map.empty[ElementType, ItemType]
    private var statusMaterial = Map.empty[StatusType, ItemType]
    private var bonusMaterial = Map.empty[BonusType, ItemType]

    def getElementMaterial(elementType: ElementType, level: Int): ItemType = {
      elementMaterial.get(elementType) match {
        case Some(e) => e
        case None =>
          val e = MaterialFactory.createMaterialFromElement(elementType, level)
          elementMaterial += (elementType -> e)
          e
      }
    }

    def getStatusMaterial(statusType: StatusType, level: Int): ItemType = {
      statusMaterial.get(statusType) match {
        case Some(e) => e
        case None =>
          val e = MaterialFactory.createMaterialFromStatus(statusType, level)
          statusMaterial += (statusType -> e)
          e
      }
    }

    def getBonusMaterial(bonusType: BonusType, level: Int): ItemType = {
      bonusMaterial.get(bonusType) match {
        case Some(e) => e
        case None =>
          val e = MaterialFactory.createMaterialFromBonus(bonusType, level)
          bonusMaterial += (bonusType -> e)
          e
      }
    }
  }

  def craftItemType(craftStep: CraftStep): Unit = {
    if (craftStep.itemTypeRoot.getLevel == config.getLevelMin) {
      for (element <- ElementType.values) {
        val resultCategory = craftStep.categoryRoot.copy.withElementCategory(element)
        val result = createItemType(resultCategory, craftStep.itemTypeRoot.getLevel + 1)
        val resultDescription = createDescription(resultCategory)
        result.setName(resultDescription.getDescription)
        val material = craftStep.materialPool.getElementMaterial(element, craftStep.itemTypeRoot.getLevel)
        craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
        craftItemType(CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
      }
    } else if (craftStep.itemTypeRoot.getLevel == config.getLevelMin + 1) {
      for (status <- StatusType.values) {
        val resultCategory = craftStep.categoryRoot.copy.withStatusCategory(status)
        val result = createItemType(resultCategory, craftStep.itemTypeRoot.getLevel + 1)
        val resultDescription = createDescription(resultCategory)
        result.setName(resultDescription.getDescription)
        val material = craftStep.materialPool.getStatusMaterial(status, craftStep.itemTypeRoot.getLevel)
        craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
        craftItemType(CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
      }
    } else if (craftStep.itemTypeRoot.getLevel == config.getLevelMin + 2) {
      for (bonus <- BonusType.values) {
        val resultCategory = craftStep.categoryRoot.copy.withBonusCategory(bonus)
        val result = createItemType(resultCategory, craftStep.itemTypeRoot.getLevel + 1)
        val resultDescription = createDescription(resultCategory)
        result.setName(resultDescription.getDescription)
        val material = craftStep.materialPool.getBonusMaterial(bonus, craftStep.itemTypeRoot.getLevel)
        craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
        craftItemType(CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
      }
    }
  }

  def generateCraft: Crafts = {
    val crafts = new DefaultCrafts
    val materialPool = new MaterialPool
    // init
    for (natureCategory <- NatureType.values) {
      val categoryRoot = (new CategoryBuilder).withNatureCategory(natureCategory)
      val itemTypeRoot = createItemType(categoryRoot, config.getLevelMin)
      itemTypeRoot.setName(createDescription(categoryRoot).getDescription)
      craftItemType(CraftStep(itemTypeRoot, categoryRoot, crafts, materialPool))
    }
    crafts
  }
}
