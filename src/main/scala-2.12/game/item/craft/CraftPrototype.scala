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

    def getNature(natureType: NatureType): String = natureType match {
      case WEAPON => "sword"
      case HELMET => "helmet"
      case PLASTRON => "plastron"
      case GREAVE => "greave"
      case SLEEVE => "sleeve"
      case CHARM => "charm"
    }
  }

  object BonusType extends Enumeration {
    type BonusType = Value
    val ARMOR, DAMAGE, NONE = Value

    def getAdjective(bonusType: BonusType): String = bonusType match {
      case ARMOR => "armored"
      case DAMAGE => "brutal"
      case NONE => "balanced"
    }
  }

  class CategoryBuilder {
    private var natureType: NatureType = NatureType.WEAPON
    private var elementType: ElementType = ElementType.NONE
    private var statusType: StatusType = StatusType.NONE
    private var bonusType: BonusType = BonusType.NONE


    def getNatureCategory: NatureType = natureType

    def getElementCategory: ElementType = elementType

    def getStatusCategory: StatusType = statusType

    def getBonusCategory: BonusType = bonusType

    def addNatureCategory(natureCategory: NatureType): CategoryBuilder = {
      this.natureType = natureCategory
      this
    }

    def addElementCategory(elementCategory: ElementType): CategoryBuilder = {
      this.elementType = elementCategory
      this
    }

    def addStatusCategory(statusCategory: StatusType): CategoryBuilder = {
      this.statusType = statusCategory
      this
    }

    def addBonusCategory(bonusCategory: BonusType): CategoryBuilder = {
      this.bonusType = bonusCategory
      this
    }

    def copy: CategoryBuilder = {
      val copy = new CategoryBuilder
      copy.addNatureCategory(natureType)
      copy.addElementCategory(elementType)
      copy.addStatusCategory(statusType)
      copy.addBonusCategory(bonusType)
      copy
    }
  }

  def createItemType(categoryBuilder: CategoryBuilder, level: Int): DefaultItemType = {
    var itemType = categoryBuilder.getNatureCategory match {
      case NatureType.WEAPON => DefaultItemType.createWeapon(level, getRandomValue(level, config.getDamageBase))
      case NatureType.CHARM => DefaultItemType.createCharm(level, getRandomSlot)
      case NatureType.HELMET => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.HEAD)
      case NatureType.PLASTRON => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.BODY)
      case NatureType.SLEEVE => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.ARMS)
      case NatureType.GREAVE => DefaultItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.LEGS)
    }
    itemType = categoryBuilder.getElementCategory match {
      case e if e != ElementType.NONE => Element(itemType, e)
      case _ => itemType
    }
    itemType = categoryBuilder.getStatusCategory match {
      case s if s != StatusType.NONE => Status(itemType, s)
      case _ => itemType
    }
    itemType = categoryBuilder.getBonusCategory match {
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
    descriptionBuilder.addNature(NatureType.getNature(categoryBuilder.getNatureCategory))
    descriptionBuilder.addElement(ElementType.getAdjective(categoryBuilder.getElementCategory))
    descriptionBuilder.addAdjective(StatusType.getAdjective(categoryBuilder.getStatusCategory))
    descriptionBuilder.addAdjective(BonusType.getAdjective(categoryBuilder.getBonusCategory))
    descriptionBuilder
  }

  object MaterialFactory {

    def createMaterialFromElement(elementType: ElementType, level: Int): DefaultItemType = {
      val descriptionBuilder = new DescriptionBuilder().addNature("material").addElement(ElementType.getAdjective(elementType))
      DefaultItemType.createMaterial(descriptionBuilder.getDescription, level)
    }

    def createMaterialFromStatus(statusType: StatusType, level: Int): DefaultItemType = {
      val descriptionBuilder = new DescriptionBuilder().addNature("material").addElement(StatusType.getAdjective(statusType))
      DefaultItemType.createMaterial(descriptionBuilder.getDescription, level)
    }

    def createMaterialFromBonus(bonusType: BonusType, level: Int): DefaultItemType = {
      val descriptionBuilder = new DescriptionBuilder().addNature("material").addElement(BonusType.getAdjective(bonusType))
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
