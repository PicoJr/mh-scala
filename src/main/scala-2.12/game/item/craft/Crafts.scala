package game.item.craft

import game.id.Identifiable
import game.item.ItemType

/** Holds recipes for crafts
  * itemType + material -> itemType
  * Created by nol on 21/11/17.
  */
trait Crafts[TItemType <: ItemType] {


  type Id = Identifiable.Id

  /** Returns all craft recipes
    *
    * @return all craft recipes
    */
  def getRecipes: Map[(TItemType, TItemType), TItemType]

  /** TODO */
  def findCraftResult(id1: Id, id2: Id): Option[TItemType]

  /** Add new recipe
    *
    * @param itemType non material
    * @param material for the craft
    * @param result   of the craft (non material)
    */
  def addRecipe(itemType: TItemType, material: TItemType, result: TItemType): Unit

  /** Returns recipes with i as ingredient
    *
    * @param i item
    * @return all recipes for which i is an ingredient (not a result)
    */
  def getRecipesWith(i: TItemType): Map[(TItemType, TItemType), TItemType] = {
    getRecipes.filterKeys { case (i1, i2) => i.getUniqueId == i1.getUniqueId || i.getUniqueId == i2.getUniqueId }
  }

  /** Get all materials from recipes with 'level' level
    *
    * @param level of materials
    * @return all materials from recipes with 'level' level
    */
  def getMaterials(level: Int): Seq[TItemType]

  /** Get all non materials from recipes with 'level' level
    *
    * @param level of non materials
    * @return all non materials from recipes with 'level' level
    */
  def getNonMaterials(level: Int): Seq[TItemType]

}
