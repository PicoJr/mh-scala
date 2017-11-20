package game.item.craft

import game.item._

/** Holds all craft recipes for all item types.
  * Created by nol on 14/11/17.
  */
class Crafts {
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

  def getRecipes: Map[(ItemType, ItemType), ItemType] = recipes

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
