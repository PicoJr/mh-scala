package game.item.craft

import game.item._

/** Default Crafts backed by a Map, unoptimized
  * Created by nol on 14/11/17.
  */
class DefaultCrafts extends Crafts {
  private var recipes: Map[(ItemType, ItemType), ItemType] = Map.empty

  override def addRecipe(i1: ItemType, material: ItemType, result: ItemType): Unit = {
    recipes += ((i1, material) -> result)
  }

  override def getRecipes: Map[(ItemType, ItemType), ItemType] = recipes

  override def findCraftResult(itemType: ItemType, material: ItemType): Option[ItemType] = {
    val p = (key: ((ItemType, ItemType), ItemType)) => key match {
      case ((i1, i2), _) =>
        (i1.getUniqueId == itemType.getUniqueId) && (i2.getUniqueId == material.getUniqueId)
      case _ => false
    }
    getRecipes.find(p) match {
      case Some(((_, _), result)) => Some(result)
      case _ => Option.empty
    }
  }

  override def getMaterials(level: Int): Seq[ItemType] = {
    filterAll(i => i.isMaterial && i.getLevel == level)
  }

  override def getNonMaterials(level: Int): Seq[ItemType] = {
    filterAll(i => !i.isMaterial && i.getLevel == level)
  }

  private def filterAll(p: ItemType => Boolean): Seq[ItemType] = {
    var matching = Seq.empty[ItemType]
    for (recipe <- getRecipes) {
      recipe match {
        case ((i1, i2), i3) =>
          if (p(i1)) matching = matching :+ i1
          if (p(i2)) matching = matching :+ i2
          if (p(i3)) matching = matching :+ i3
      }
    }
    matching
  }
}
