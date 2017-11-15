package procedural

import config.Config
import item.Classification.Classification
import item._

import scala.util.Random

/**
  * Created by nol on 14/11/17.
  */
class Crafts private {
  private var recipes: Map[(ItemType, ItemType), ItemType] = Map.empty

  def craftItemType(i1: ItemType, i2: ItemType): Option[ItemType] = {
    recipes.get((i1, i2))
  }

  def addRecipe(i1: ItemType, material: ItemType, result: ItemType): Unit = {
    require(material.isMaterial)
    recipes += ((i1, material) -> result)
  }

  def getMaterials(level: Int): Seq[ItemType] = {
    var materials: Seq[ItemType] = Seq.empty
    for (key <- recipes.keys) {
      key match {
        case (i1, i2) =>
          if (i1.getLevel == level && i1.isMaterial) materials = materials :+ i1
          if (i2.getLevel == level && i2.isMaterial) materials = materials :+ i2
        case _ =>
      }
    }
    materials
  }

  def getRecipesWith(i: Item): Map[(ItemType, ItemType), ItemType] = {
    recipes.filterKeys { case (i1, i2) => i.is(i1) || i.is(i2) }
  }
}


object Crafts {

  private def classificationsAt(level: Int): Seq[Classification] = {
    var classifications = Seq.empty[Classification]
    if (level >= Config.LEVEL_MIN) {
      classifications ++= Seq(Classification.PROTECTION, Classification.DAMAGE)
    } else if (level >= (Config.LEVEL_MIN + 1)) {
      classifications ++= Seq(Classification.ELEMENT)
    } else if (level >= (Config.LEVEL_MIN + 2)) {
      classifications ++= Seq(Classification.CHARM_SLOT)
    } else if (level >= (Config.LEVEL_MIN + 3)) {
      classifications ++= Seq(Classification.STATUS)
    }
    classifications
  }

  def createWeapons(level: Int): Seq[ItemType] = {
    var weapons = Seq.empty[ItemType]
    for (_ <- 1 to Config.WEAPONS_PER_LEVEL) {
      val classifications = classificationsAt(level)
      weapons = weapons :+ RandomItemTypeFactory.createWeaponType(level, classifications: _*)
    }
    weapons
  }

  def createArmors(level: Int): Seq[ItemType] = {
    var armors = Seq.empty[ItemType]
    for (_ <- 1 to Config.ARMORS_PER_LEVEL) {
      val classifications = classificationsAt(level)
      // 4 parts -> 1 armor
      armors = armors :+ RandomItemTypeFactory.createArmorType(level, ArmorPart.HEAD, classifications: _*)
      armors = armors :+ RandomItemTypeFactory.createArmorType(level, ArmorPart.BODY, classifications: _*)
      armors = armors :+ RandomItemTypeFactory.createArmorType(level, ArmorPart.ARMS, classifications: _*)
      armors = armors :+ RandomItemTypeFactory.createArmorType(level, ArmorPart.LEGS, classifications: _*)
    }
    armors
  }

  def createCharms(level: Int): Seq[ItemType] = {
    var charms = Seq.empty[ItemType]
    for (_ <- 1 to Config.CHARMS_PER_LEVEL) {
      val classifications = classificationsAt(level)
      charms = charms :+ RandomItemTypeFactory.createCharmType(level, classifications: _*)
    }
    charms
  }

  def createItemTypes(level: Int): Seq[ItemType] = {
    createArmors(level) ++ createWeapons(level) ++ createCharms(level)
  }

  def createMaterials(level: Int): Seq[ItemType] = {
    var materials = Seq.empty[ItemType]
    for (_ <- 1 to Config.MATERIALS_PER_LEVEL) {
      materials = materials :+ RandomItemTypeFactory.createMaterialType(level)
    }
    materials
  }

  def generateCraftRecipes: Crafts = {
    val crafts = new Crafts
    var itemTypes: Map[Int, Seq[ItemType]] = Map.empty
    var materials: Map[Int, Seq[ItemType]] = Map.empty
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      itemTypes += (level -> createItemTypes(level))
      materials += (level -> createMaterials(level))
    }
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      for (result <- itemTypes(level)) {
        val itemsPreviousLevel = itemTypes(level)
        val itemTypePreviousLevel = itemsPreviousLevel(Random.nextInt(itemsPreviousLevel.size))
        val materialsPreviousLevel = materials(level)
        val materialPreviousLevel = materialsPreviousLevel(Random.nextInt(materialsPreviousLevel.size))
        crafts.addRecipe(itemTypePreviousLevel, materialPreviousLevel, result)
      }
    }
    crafts
  }

}
