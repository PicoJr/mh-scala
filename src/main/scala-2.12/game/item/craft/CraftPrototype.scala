package game.item.craft

import game.config.ConfigLoader
import game.item.StatusType.StatusType
import game.item._
import game.item.craft.CraftPrototype.BonusType.BonusType
import game.item.craft.CraftPrototype.NatureType.NatureType
import game.item.element.ElementType
import game.item.element.ElementType.ElementType
import game.util.Procedural

/**
  * Created by nol on 20/11/17.
  */
object CraftPrototype {

  private val config = ConfigLoader.loadGameConfig

  private def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  private def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, config.getStatsGrowth, config.getPercentageVariation)

  object NatureType extends Enumeration {
    type NatureType = Value
    val WEAPON, HELMET, PLASTRON, GREAVE, SLEEVE, CHARM = Value
  }

  object BonusType extends Enumeration {
    type BonusType = Value
    val ARMOR, DAMAGE, NONE = Value
  }

  class CategoryBuilder {
    private var natureType: Option[NatureType] = Option.empty
    private var elementType: Option[ElementType] = Option.empty
    private var statusType: Option[StatusType] = Option.empty
    private var bonusType: Option[BonusType] = Option.empty


    def getNatureCategory: Option[NatureType] = natureType

    def getElementCategory: Option[ElementType] = elementType

    def getStatusCategory: Option[StatusType] = statusType

    def getBonusCategory: Option[BonusType] = bonusType

    def addNatureCategory(natureCategory: NatureType): CategoryBuilder = {
      this.natureType = Some(natureCategory)
      this
    }

    def addElementCategory(elementCategory: ElementType): CategoryBuilder = {
      this.elementType = Some(elementCategory)
      this
    }

    def addStatusCategory(statusCategory: StatusType): CategoryBuilder = {
      this.statusType = Some(statusCategory)
      this
    }

    def addBonusCategory(bonusCategory: BonusType): CategoryBuilder = {
      this.bonusType = Some(bonusCategory)
      this
    }

    def copy: CategoryBuilder = {
      val copy = new CategoryBuilder
      if (natureType.nonEmpty) copy.addNatureCategory(natureType.get)
      if (elementType.nonEmpty) copy.addElementCategory(elementType.get)
      if (statusType.nonEmpty) copy.addStatusCategory(statusType.get)
      if (bonusType.nonEmpty) copy.addBonusCategory(bonusType.get)
      copy
    }
  }

  def createItemType(categoryBuilder: CategoryBuilder, level: Int): DefaultItemType = {
    var itemType = categoryBuilder.getNatureCategory.getOrElse(NatureType.WEAPON) match {
      case NatureType.WEAPON => DefaultItemType.createWeapon(level, getRandomValue(level, config.getDamageBase))
      case NatureType.CHARM => DefaultItemType.createCharm(level, getRandomSlot)
      case NatureType.HELMET => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.HEAD)
      case NatureType.PLASTRON => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.BODY)
      case NatureType.SLEEVE => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.ARMS)
      case NatureType.GREAVE => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.LEGS)
    }
    itemType = categoryBuilder.getElementCategory.getOrElse(ElementType.NONE) match {
      case e if e != ElementType.NONE => Element(itemType, e)
      case _ => itemType
    }
    itemType = categoryBuilder.getStatusCategory.getOrElse(StatusType.NONE) match {
      case s if s != StatusType.NONE => Status(itemType, s)
      case _ => itemType
    }
    itemType = categoryBuilder.getBonusCategory.getOrElse(BonusType.NONE) match {
      case BonusType.DAMAGE if !itemType.isWeapon =>
        Damage(itemType, getRandomValue(level, config.getDamageBonusBase))
      case BonusType.ARMOR if !itemType.isArmor =>
        Protection(itemType, getRandomValue(level, config.getArmorBonusBase))
      case _ => itemType
    }
    itemType
  }

  def createDescription(categoryBuilder: CategoryBuilder): DescriptionBuilder = {
    val descriptionBuilder = new DescriptionBuilder()
    categoryBuilder.getNatureCategory.getOrElse(NatureType.WEAPON) match {
      case NatureType.WEAPON => descriptionBuilder.addNature("sword")
      case NatureType.CHARM => descriptionBuilder.addNature("charm")
      case NatureType.HELMET => descriptionBuilder.addNature("helmet")
      case NatureType.PLASTRON => descriptionBuilder.addNature("plastron")
      case NatureType.SLEEVE => descriptionBuilder.addNature("sleeve")
      case NatureType.GREAVE => descriptionBuilder.addNature("greave")
    }
    categoryBuilder.getElementCategory.getOrElse(ElementType.NONE) match {
      case ElementType.FIRE => descriptionBuilder.addAdjective("burning")
      case ElementType.WATER => descriptionBuilder.addAdjective("flowing")
      case _ =>
    }
    categoryBuilder.getStatusCategory.getOrElse(StatusType.NONE) match {
      case StatusType.SLEEP => descriptionBuilder.addAdjective("tranquilizing")
      case StatusType.STUN => descriptionBuilder.addAdjective("stunning")
      case _ =>
    }
    categoryBuilder.getBonusCategory.getOrElse(BonusType.NONE) match {
      case BonusType.ARMOR => descriptionBuilder.addAdjective("armored")
      case BonusType.DAMAGE => descriptionBuilder.addAdjective("brutal")
      case _ =>
    }
    descriptionBuilder
  }

  object MaterialFactory {

    def createMaterialFromElement(elementType: ElementType, level: Int): DefaultItemType = {
      DefaultItemType.createMaterial(elementType.toString + "material", level)
    }

    def createMaterialFromStatus(statusType: StatusType, level: Int): DefaultItemType = {
      DefaultItemType.createMaterial(statusType.toString + "material", level)
    }

    def createMaterialFromBonus(bonusType: BonusType, level: Int): DefaultItemType = {
      DefaultItemType.createMaterial(bonusType.toString + "material", level)
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
          elementMaterial = elementMaterial + (elementType -> e)
          e
      }
    }

    def getStatusMaterial(statusType: StatusType, level: Int): ItemType = {
      statusMaterial.get(statusType) match {
        case Some(e) => e
        case None =>
          val e = MaterialFactory.createMaterialFromStatus(statusType, level)
          statusMaterial = statusMaterial + (statusType -> e)
          e
      }
    }

    def getBonusMaterial(bonusType: BonusType, level: Int): ItemType = {
      bonusMaterial.get(bonusType) match {
        case Some(e) => e
        case None =>
          val e = MaterialFactory.createMaterialFromBonus(bonusType, level)
          bonusMaterial = bonusMaterial + (bonusType -> e)
          e
      }
    }
  }

  def craftItemType(craftStep: CraftStep): Unit = {
    if (craftStep.itemTypeRoot.getLevel == config.getLevelMin) {
      for (element <- ElementType.values) {
        val resultCategory = craftStep.categoryRoot.copy.addElementCategory(element)
        val result = createItemType(resultCategory, craftStep.itemTypeRoot.getLevel + 1)
        val resultDescription = createDescription(resultCategory)
        result.setName(resultDescription.getDescription)
        val material = craftStep.materialPool.getElementMaterial(element, craftStep.itemTypeRoot.getLevel)
        craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
        craftItemType(CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
      }
    } else if (craftStep.itemTypeRoot.getLevel == config.getLevelMin + 1) {
      for (status <- StatusType.values) {
        val resultCategory = craftStep.categoryRoot.copy.addStatusCategory(status)
        val result = createItemType(resultCategory, craftStep.itemTypeRoot.getLevel + 1)
        val resultDescription = createDescription(resultCategory)
        result.setName(resultDescription.getDescription)
        val material = craftStep.materialPool.getStatusMaterial(status, craftStep.itemTypeRoot.getLevel)
        craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
        craftItemType(CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
      }
    } else if (craftStep.itemTypeRoot.getLevel == config.getLevelMin + 2) {
      for (bonus <- BonusType.values) {
        val resultCategory = craftStep.categoryRoot.copy.addBonusCategory(bonus)
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
    // init
    for (natureCategory <- NatureType.values) {
      val categoryRoot = (new CategoryBuilder).addNatureCategory(natureCategory)
      val itemTypeRoot = createItemType(categoryRoot, config.getLevelMin)
      itemTypeRoot.setName(createDescription(categoryRoot).getDescription)
      craftItemType(CraftStep(itemTypeRoot, categoryRoot, crafts, new MaterialPool))
    }
    crafts
  }
}
