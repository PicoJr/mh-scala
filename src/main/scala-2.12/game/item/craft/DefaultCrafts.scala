package game.item.craft

import game.item._

/** Default Crafts backed by a Map, unoptimized
  * Created by nol on 14/11/17.
  */
class DefaultCrafts[TItemType <: ItemType] extends Crafts[TItemType] {
  private var recipes = Map.empty[(TItemType, TItemType), TItemType]

  override def addRecipe(i1: TItemType, material: TItemType, result: TItemType): Unit = {
    recipes += ((i1, material) -> result)
  }

  override def getRecipes: Map[(TItemType, TItemType), TItemType] = recipes

  override def findCraftResult(id1: Id, id2: Id): Option[TItemType] = {
    val p = (key: ((ItemType, ItemType), ItemType)) => key._1._1.getUniqueId == id1 && key._1._2.getUniqueId == id2
    getRecipes.find(p) match {
      case Some(((_, _), result)) => Some(result)
      case _ => Option.empty
    }
  }

  override def getMaterials(level: Int): Seq[TItemType] = {
    filterAll(i => i.isMaterial && i.getLevel == level)
  }

  override def getNonMaterials(level: Int): Seq[TItemType] = {
    filterAll(i => !i.isMaterial && i.getLevel == level)
  }

  private def filterAll(p: TItemType => Boolean): Seq[TItemType] = {
    var matching = Seq.empty[TItemType]
    for (recipe <- getRecipes) {
      if (p(recipe._1._1)) matching = matching :+ recipe._1._1
      if (p(recipe._1._2)) matching = matching :+ recipe._1._2
      if (p(recipe._2)) matching = matching :+ recipe._2
    }
    matching
  }
}
