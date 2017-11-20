package game.item.craft

import game.config.ConfigLoader
import game.item.ElementType.ElementType
import game.item.StatusType.StatusType
import game.item._
import game.item.craft.CraftPrototype.BonusCategory.BonusCategory
import game.item.craft.CraftPrototype.NatureCategory.NatureCategory
import game.util.Procedural

/**
  * Created by nol on 20/11/17.
  */
object CraftPrototype {

  private val config = ConfigLoader.loadGameConfig

  private def getRandomSlot: Int = Procedural.pickRandom(1, 2, 3).get

  private def getRandomValue(level: Int, base: Int): Int = Procedural.getRandomValue(level, base, config.getStatsGrowth, config.getPercentageVariation)

  object NatureCategory extends Enumeration {
    type NatureCategory = Value
    val WEAPON, HELMET, PLASTRON, GREAVE, SLEEVE, CHARM = Value
  }

  object BonusCategory extends Enumeration {
    type BonusCategory = Value
    val ARMOR, DAMAGE, NONE = Value
  }

  class CategoryBuilder {
    private var natureCategory: Option[NatureCategory] = Option.empty
    private var elementCategory: Option[ElementType] = Option.empty
    private var statusCategory: Option[StatusType] = Option.empty
    private var bonusCategory: Option[BonusCategory] = Option.empty


    def getNatureCategory: Option[NatureCategory] = natureCategory

    def getElementCategory: Option[ElementType] = elementCategory

    def getStatusCategory: Option[StatusType] = statusCategory

    def getBonusCategory: Option[BonusCategory] = bonusCategory

    def addNatureCategory(natureCategory: NatureCategory): CategoryBuilder = {
      this.natureCategory = Some(natureCategory)
      this
    }

    def addElementCategory(elementCategory: ElementType): CategoryBuilder = {
      this.elementCategory = Some(elementCategory)
      this
    }

    def addStatusCategory(statusCategory: StatusType): CategoryBuilder = {
      this.statusCategory = Some(statusCategory)
      this
    }

    def addBonusCategory(bonusCategory: BonusCategory): CategoryBuilder = {
      this.bonusCategory = Some(bonusCategory)
      this
    }

    def copy: CategoryBuilder = {
      val copy = new CategoryBuilder
      if (natureCategory.nonEmpty) copy.addNatureCategory(natureCategory.get)
      if (elementCategory.nonEmpty) copy.addElementCategory(elementCategory.get)
      if (statusCategory.nonEmpty) copy.addStatusCategory(statusCategory.get)
      if (bonusCategory.nonEmpty) copy.addBonusCategory(bonusCategory.get)
      copy
    }
  }

  def createItemType(categoryBuilder: CategoryBuilder, level: Int): ItemType = {
    var itemType = categoryBuilder.getNatureCategory.getOrElse(NatureCategory.WEAPON) match {
      case NatureCategory.WEAPON => ItemType.createWeapon(level, getRandomValue(level, config.getDamageBase))
      case NatureCategory.CHARM => ItemType.createCharm(level, getRandomSlot)
      case NatureCategory.HELMET => ItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.HEAD)
      case NatureCategory.PLASTRON => ItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.BODY)
      case NatureCategory.SLEEVE => ItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.ARMS)
      case NatureCategory.GREAVE => ItemType.createArmor(level, getRandomValue(level, config.getArmorBase), ArmorPart.LEGS)
    }
    itemType = categoryBuilder.getElementCategory.getOrElse(ElementType.NONE) match {
      case e if e != ElementType.NONE => Element(itemType, e)
      case _ => itemType
    }
    itemType = categoryBuilder.getStatusCategory.getOrElse(StatusType.NONE) match {
      case s if s != StatusType.NONE => Status(itemType, s)
      case _ => itemType
    }
    itemType = categoryBuilder.getBonusCategory.getOrElse(BonusCategory.NONE) match {
      case BonusCategory.DAMAGE if !itemType.isWeapon =>
        Damage(itemType, getRandomValue(level, config.getDamageBonusBase))
      case BonusCategory.ARMOR if !itemType.isArmor =>
        Protection(itemType, getRandomValue(level, config.getArmorBonusBase))
      case _ => itemType
    }
    itemType
  }

  def createDescription(categoryBuilder: CategoryBuilder): DescriptionBuilder = {
    val descriptionBuilder = new DescriptionBuilder()
    categoryBuilder.getNatureCategory.getOrElse(NatureCategory.WEAPON) match {
      case NatureCategory.WEAPON => descriptionBuilder.addNature("weapon")
      case NatureCategory.CHARM => descriptionBuilder.addNature("charm")
      case NatureCategory.HELMET => descriptionBuilder.addNature("helmet")
      case NatureCategory.PLASTRON => descriptionBuilder.addNature("plastron")
      case NatureCategory.SLEEVE => descriptionBuilder.addNature("sleeve")
      case NatureCategory.GREAVE => descriptionBuilder.addNature("greave")
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
    categoryBuilder.getBonusCategory.getOrElse(BonusCategory.NONE) match {
      case BonusCategory.ARMOR => descriptionBuilder.addAdjective("armored")
      case BonusCategory.DAMAGE => descriptionBuilder.addAdjective("violent")
      case _ =>
    }
    descriptionBuilder
  }

  def createMaterial(categoryBuilder: CategoryBuilder, level: Int): ItemType = {
    ItemType.createMaterial("material", level)
  }

  case class CraftAtLevel(nextLevel: Int, crafts: Crafts, categories: Seq[CategoryBuilder])

  def addCraftAtLevel(craftAtLevel: CraftAtLevel): CraftAtLevel = {
    def createRecipe(craftAtLevel: CraftAtLevel, category: CategoryBuilder, resultCategory: CategoryBuilder): Unit = {
      val itemType = createItemType(category, craftAtLevel.nextLevel - 1)
      itemType.setName(createDescription(category).getDescription)
      val result = createItemType(category, craftAtLevel.nextLevel)
      result.setName(createDescription(resultCategory).getDescription)
      val material = createMaterial(resultCategory, craftAtLevel.nextLevel - 1)
      val resultDescription = createDescription(resultCategory)
      val materialDescription = resultDescription.addAdjective(resultDescription.getNature).addNature("material")
      material.setName(materialDescription.getDescription)
      craftAtLevel.crafts.addRecipe(itemType, material, result)
    }

    var categories = Seq.empty[CategoryBuilder]
    if (craftAtLevel.nextLevel == config.getLevelMin) {
      for (natureCategory <- NatureCategory.values) {
        categories = categories :+ (new CategoryBuilder).addNatureCategory(natureCategory)
      }
      CraftAtLevel(config.getLevelMin + 1, new Crafts, categories)
    } else if (craftAtLevel.nextLevel == config.getLevelMin + 1) {
      for (category <- craftAtLevel.categories) {
        for (elementCategory <- ElementType.values) {
          val resultCategory = category.copy.addElementCategory(elementCategory)
          createRecipe(craftAtLevel, category, resultCategory)
          categories = categories :+ resultCategory
        }
      }
      CraftAtLevel(craftAtLevel.nextLevel + 1, craftAtLevel.crafts, categories)
    } else if (craftAtLevel.nextLevel == config.getLevelMin + 2) {
      for (category <- craftAtLevel.categories) {
        for (statusCategory <- StatusType.values) {
          val resultCategory = category.copy.addStatusCategory(statusCategory)
          createRecipe(craftAtLevel, category, resultCategory)
          categories = categories :+ resultCategory
        }
      }
      CraftAtLevel(craftAtLevel.nextLevel + 1, craftAtLevel.crafts, categories)
    } else if (craftAtLevel.nextLevel == config.getLevelMin + 3) {
      for (category <- craftAtLevel.categories) {
        for (bonusCategory <- BonusCategory.values) {
          val resultCategory = category.copy.addBonusCategory(bonusCategory)
          createRecipe(craftAtLevel, category, resultCategory)
          categories = categories :+ resultCategory
        }
      }
      CraftAtLevel(craftAtLevel.nextLevel + 1, craftAtLevel.crafts, categories)
    } else {
      CraftAtLevel(craftAtLevel.nextLevel + 1, craftAtLevel.crafts, craftAtLevel.categories)
    }
  }

  def generateCraft: Crafts = {
    var craftAtLevel = CraftAtLevel(config.getLevelMin, new Crafts, Seq.empty[CategoryBuilder])
    for (_ <- config.getLevelMin to config.getLevelMax) {
      craftAtLevel = addCraftAtLevel(craftAtLevel)
    }
    craftAtLevel.crafts
  }
}
