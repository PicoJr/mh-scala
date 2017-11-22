package game.item.craft

import game.item.{Item, ItemType}

/** Holds recipes for crafts
  * itemType + material -> itemType
  * Created by nol on 21/11/17.
  */
trait Crafts {

  /** Returns all craft recipes
    *
    * @return all craft recipes
    */
  def getRecipes: Map[(ItemType, ItemType), ItemType]

  /** Find craft result of itemType + material if any else None
    *
    * @param itemType non material
    * @param material for the craft
    * @return result of itemType+ material if any else None
    */
  def findCraftResult(itemType: ItemType, material: ItemType): Option[ItemType] = {
    getRecipes.get((itemType, material))
  }

  /** Add new recipe
    *
    * @param itemType non material
    * @param material for the craft
    * @param result   of the craft (non material)
    */
  def addRecipe(itemType: ItemType, material: ItemType, result: ItemType): Unit

  /** Returns recipes with i as ingredient
    *
    * @param i item
    * @return all recipes for which i is an ingredient (not a result)
    */
  def getRecipesWith(i: Item): Map[(ItemType, ItemType), ItemType] = {
    getRecipes.filterKeys { case (i1, i2) => i.isItemType(i1) || i.isItemType(i2) }
  }

  /** Get all materials from recipes with 'level' level
    *
    * @param level of materials
    * @return all materials from recipes with 'level' level
    */
  def getMaterials(level: Int): Seq[ItemType]

  /** Get all non materials from recipes with 'level' level
    *
    * @param level of non materials
    * @return all non materials from recipes with 'level' level
    */
  def getNonMaterials(level: Int): Seq[ItemType]

}
