package game.item.craft

import game.config.ConfigLoader
import game.item.Classification.Classification
import game.item._
import game.util.Procedural

/** Holds all craft recipes for all item types.
  * Created by nol on 14/11/17.
  */
class Crafts private {
  private var recipes: Map[(ItemType, ItemType), ItemType] = Map.empty

  private def filterIngredients(p: ItemType => Boolean): Seq[ItemType] = {
    var matching: Seq[ItemType] = Seq.empty
    for (key <- recipes.keys) {
      key match {
        case (i1, i2) =>
          if (p(i1)) matching = matching :+ i1
          if (p(i2)) matching = matching :+ i2
        case _ =>
      }
    }
    matching
  }

  def craftItemType(i1: ItemType, i2: ItemType): Option[ItemType] = {
    recipes.get((i1, i2))
  }

  def addRecipe(i1: ItemType, material: ItemType, result: ItemType): Unit = {
    require(material.isMaterial)
    recipes += ((i1, material) -> result)
  }

  def getMaterials(level: Int): Seq[ItemType] = {
    filterIngredients(i => i.isMaterial && i.getLevel == level)
  }

  def getNonMaterial(level: Int): Seq[ItemType] = {
    filterIngredients(i => !i.isMaterial && i.getLevel == level)
  }

  def getRecipesWith(i: Item): Map[(ItemType, ItemType), ItemType] = {
    recipes.filterKeys { case (i1, i2) => i.isItemType(i1) || i.isItemType(i2) }
  }
}


object Crafts {

  private final val config = ConfigLoader.loadConfig

  private def armorsClassificationsAt(level: Int): Seq[Classification] = {
    var classifications = Seq.empty[Classification]
    if (level >= config.getLevelMin) {
      classifications ++= Seq(Classification.DAMAGE)
    }
    if (level >= (config.getLevelMin + 1)) {
      classifications ++= Seq(Classification.ELEMENT)
    }
    if (level >= (config.getLevelMin + 2)) {
      classifications ++= Seq(Classification.CHARM_SLOT)
    }
    if (level >= (config.getLevelMin + 3)) {
      classifications ++= Seq(Classification.STATUS)
    }
    classifications
  }

  private def weaponsClassificationsAt(level: Int): Seq[Classification] = {
    var classifications = Seq.empty[Classification]
    if (level >= config.getLevelMin) {
      classifications ++= Seq(Classification.PROTECTION)
    }
    if (level >= (config.getLevelMin + 1)) {
      classifications ++= Seq(Classification.ELEMENT)
    }
    if (level >= (config.getLevelMin + 2)) {
      classifications ++= Seq(Classification.CHARM_SLOT)
    }
    if (level >= (config.getLevelMin + 3)) {
      classifications ++= Seq(Classification.STATUS)
    }
    classifications
  }

  private def charmsClassificationsAt(level: Int): Seq[Classification] = {
    var classifications = Seq.empty[Classification]
    if (level >= config.getLevelMin) {
      classifications ++= Seq(Classification.DAMAGE, Classification.PROTECTION)
    }
    if (level >= (config.getLevelMin + 1)) {
      classifications ++= Seq(Classification.ELEMENT)
    }
    if (level >= (config.getLevelMin + 2)) {
      classifications ++= Seq(Classification.STATUS)
    }
    classifications
  }

  private def createWeapons(level: Int): Seq[ItemType] = {
    var weapons = Seq.empty[ItemType]
    for (_ <- 1 to config.getWeaponsPerLevel) {
      val classifications = weaponsClassificationsAt(level)
      weapons = weapons :+ RandomItemTypeFactory.createWeaponType(level, classifications: _*)
    }
    weapons
  }

  private def createArmors(level: Int): Seq[ItemType] = {
    var armors = Seq.empty[ItemType]
    for (_ <- 1 to config.getArmorsPerLevel) {
      val classifications = armorsClassificationsAt(level)
      // 4 parts -> 1 armor
      armors = armors :+ RandomItemTypeFactory.createArmorType(level, ArmorPart.HEAD, classifications: _*)
      armors = armors :+ RandomItemTypeFactory.createArmorType(level, ArmorPart.BODY, classifications: _*)
      armors = armors :+ RandomItemTypeFactory.createArmorType(level, ArmorPart.ARMS, classifications: _*)
      armors = armors :+ RandomItemTypeFactory.createArmorType(level, ArmorPart.LEGS, classifications: _*)
    }
    armors
  }

  private def createCharms(level: Int): Seq[ItemType] = {
    var charms = Seq.empty[ItemType]
    for (_ <- 1 to config.getCharmsPerLevel) {
      val classifications = charmsClassificationsAt(level)
      charms = charms :+ RandomItemTypeFactory.createCharmType(level, classifications: _*)
    }
    charms
  }

  private def createItemTypes(level: Int): Seq[ItemType] = {
    createArmors(level) ++ createWeapons(level) ++ createCharms(level)
  }

  private def createMaterials(level: Int): Seq[ItemType] = {
    var materials = Seq.empty[ItemType]
    for (_ <- 1 to config.getMaterialsPerLevel) {
      materials = materials :+ RandomItemTypeFactory.createMaterialType(level)
    }
    materials
  }

  def generateCraftRecipes: Crafts = {
    val ITEM_LEVEL_MAX: Int = config.getLevelMax + 1
    val crafts = new Crafts
    var itemTypes: Map[Int, Seq[ItemType]] = Map.empty
    var materials: Map[Int, Seq[ItemType]] = Map.empty
    for (level <- config.getLevelMin to config.getLevelMax) {
      itemTypes += (level -> createItemTypes(level))
      materials += (level -> createMaterials(level))
    }
    itemTypes += (ITEM_LEVEL_MAX -> createItemTypes(ITEM_LEVEL_MAX))
    for (level <- (config.getLevelMin + 1) to ITEM_LEVEL_MAX) {
      for (result <- itemTypes(level)) {
        val itemTypePreviousLevel = Procedural.pickRandomFromSeq(itemTypes(level - 1)).get
        val materialPreviousLevel = Procedural.pickRandomFromSeq(materials(level - 1)).get
        crafts.addRecipe(itemTypePreviousLevel, materialPreviousLevel, result)
      }
    }
    crafts
  }

}
