package game.item.craft

import game.id.Identifiable
import game.item.ItemType

/** Holds recipes for crafts
  * itemType + material -> itemType
  * Created by nol on 21/11/17.
  */
trait Crafts {


  type Id = Identifiable.Id

  /** Returns all craft recipes
    *
    * @return all craft recipes
    */
  def getRecipes: Map[(ItemType, ItemType), ItemType]

  /** TODO */
  def findCraftResult(id1: Id, id2: Id): Option[ItemType]

  /** Add new recipe
    *
    * @param itemType non material
    * @param material for the craft
    * @param result   of the craft (non material)
    */
  def addRecipe(itemType: ItemType, material: ItemType, result: ItemType): Unit

  /** Returns recipes with itemType id as ingredient
    *
    * @param id itemType id
    * @return all recipes for which i is an ingredient (not a result)
    */
  def getRecipesWith(id: Id): Map[(ItemType, ItemType), ItemType] = {
    getRecipes.filterKeys { case (i1, i2) => id == i1.getUniqueId || id == i2.getUniqueId }
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
