package game.item.craft

import game.item.{ItemTrait, ItemTypeTrait}

/**
  * Created by nol on 21/11/17.
  */
trait CraftsTrait {

  def getRecipes: Map[(ItemTypeTrait, ItemTypeTrait), ItemTypeTrait]

  def getCraftResult(i1: ItemTypeTrait, i2: ItemTypeTrait): Option[ItemTypeTrait] = {
    getRecipes.get((i1, i2))
  }

  def addRecipe(i1: ItemTypeTrait, material: ItemTypeTrait, result: ItemTypeTrait): Unit

  def getRecipesWith(i: ItemTrait): Map[(ItemTypeTrait, ItemTypeTrait), ItemTypeTrait] = {
    getRecipes.filterKeys { case (i1, i2) => i.isItemType(i1) || i.isItemType(i2) }
  }

  def getMaterials(level: Int): Seq[ItemTypeTrait]

  def getNonMaterials(level: Int): Seq[ItemTypeTrait]

}
