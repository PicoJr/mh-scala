package game.item.craft

import game.item._

/** Holds all craft recipes for all item types.
  * Created by nol on 14/11/17.
  */
class Crafts extends CraftsTrait {
  private var recipes: Map[(ItemTypeTrait, ItemTypeTrait), ItemTypeTrait] = Map.empty

  def addRecipe(i1: ItemTypeTrait, material: ItemTypeTrait, result: ItemTypeTrait): Unit = {
    recipes += ((i1, material) -> result)
  }

  def getRecipes: Map[(ItemTypeTrait, ItemTypeTrait), ItemTypeTrait] = recipes

  def getMaterials(level: Int): Seq[ItemTypeTrait] = {
    var materials = Seq.empty[ItemTypeTrait]
    for (recipe <- getRecipes) {
      recipe match {
        case ((i1, i2), i3) =>
          if (i1.isMaterial) materials = materials :+ i1
          if (i2.isMaterial) materials = materials :+ i2
          if (i3.isMaterial) materials = materials :+ i3
      }
    }
    materials
  }

  def getNonMaterials(level: Int): Seq[ItemTypeTrait] = {
    var nonMaterials = Seq.empty[ItemTypeTrait]
    for (recipe <- getRecipes) {
      recipe match {
        case ((i1, i2), i3) =>
          if (!i1.isMaterial) nonMaterials = nonMaterials :+ i1
          if (!i2.isMaterial) nonMaterials = nonMaterials :+ i2
          if (!i3.isMaterial) nonMaterials = nonMaterials :+ i3
      }
    }
    nonMaterials
  }
}
