package game.item.craft

import game.config.{DefaultGameConfig, GameConfig}
import game.item._
import game.item.craft.addOn._
import game.item.craft.nature._

import scala.collection.mutable

/** Default craft factory.
  * FIXME: not very Open-Close friendly.
  * Created by nol on 20/11/17.
  */
class DefaultCraftFactory(natureTypes: Seq[NatureType], decorator: AbstractDecorator, itemTypeFactory: AbstractItemTypeFactory, gameConfig: GameConfig = DefaultGameConfig.getInstance) {

  private var craftAddOns = mutable.Map.empty[Int, Seq[AddOn]]

  def withAddOn(level: Int, addOnsAtLevel: AddOn*): Unit = {
    craftAddOns += (level -> (craftAddOns.getOrElse(level, Seq.empty[AddOn]) ++ addOnsAtLevel))
  }

  private class CraftStep(val itemTypeRoot: ItemType, val categoryRoot: CategoryBuilder, val crafts: Crafts, val materialPool: MaterialPool)

  private def craftItemType(craftStep: CraftStep): Unit = {
    def craftWithAddOn(craftStep: CraftStep, addOn: AddOn): Unit = {
      val resultCategory = craftStep.categoryRoot.copy.withAddOn(addOn)
      val result = resultCategory.create(craftStep.itemTypeRoot.getLevel + 1)
      val resultDescription = resultCategory.createDescription
      result.setName(resultDescription.getDescription)
      val material = craftStep.materialPool.getMaterial(addOn, craftStep.itemTypeRoot.getLevel)
      craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
      craftItemType(new CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
    }

    craftAddOns.get(craftStep.itemTypeRoot.getLevel) match {
      case Some(addOns) =>
        for (addon <- addOns) {
          craftWithAddOn(craftStep, addon)
        }
      case None => // nothing to add
    }
  }

  def generateCraft(crafts: Crafts): Crafts = {
    val materialPool = new MaterialPool(decorator, itemTypeFactory)
    for (natureCategory <- natureTypes) {
      val categoryRoot = new CategoryBuilder(natureCategory)
      natureCategory match {
        case Charm(_, _) => // a charm should not have charm add-ons...
        case _ => categoryRoot.withAddOn(CharmSlotAddOn(decorator))
      }
      val itemTypeRoot = categoryRoot.create(gameConfig.getLevelMin)
      itemTypeRoot.setName(categoryRoot.createDescription.getDescription)
      craftItemType(new CraftStep(itemTypeRoot, categoryRoot, crafts, materialPool))
    }
    crafts
  }

}
