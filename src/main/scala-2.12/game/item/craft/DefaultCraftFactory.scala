package game.item.craft

import game.config.{DefaultGameConfig, GameConfig}
import game.item._
import game.item.craft.addOn._
import game.item.craft.bonus.BonusType
import game.item.craft.nature._
import game.item.element._
import game.item.status.StatusType

/** Default craft factory.
  * FIXME: not very Open-Close friendly.
  * Created by nol on 20/11/17.
  */
class DefaultCraftFactory[TItemType <: ItemType](bonusTypes: Seq[BonusType], elementTypes: Seq[ElementType], natureTypes: Seq[NatureType[TItemType]], statusTypes: Seq[StatusType], decorator: AbstractDecorator[TItemType], itemTypeFactory: AbstractItemTypeFactory[TItemType], gameConfig: GameConfig = DefaultGameConfig.getGameConfig) {

  private class CraftStep(val itemTypeRoot: TItemType, val categoryRoot: CategoryBuilder[TItemType], val crafts: Crafts[TItemType], val materialPool: MaterialPool[TItemType])

  private def craftItemType(craftStep: CraftStep): Unit = {
    def craftWithAddOn(craftStep: CraftStep, addOn: AddOn[TItemType]): Unit = {
      val resultCategory = craftStep.categoryRoot.copy.withAddOn(addOn)
      val result = resultCategory.create(craftStep.itemTypeRoot.getLevel + 1)
      val resultDescription = resultCategory.createDescription
      result.setName(resultDescription.getDescription)
      val material = craftStep.materialPool.getMaterial(addOn, craftStep.itemTypeRoot.getLevel)
      craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
      craftItemType(new CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
    }

    if (craftStep.itemTypeRoot.getLevel == gameConfig.getLevelMin) {
      for (element <- elementTypes) {
        craftWithAddOn(craftStep, ElementAddOn(element, decorator))
      }
    } else if (craftStep.itemTypeRoot.getLevel == gameConfig.getLevelMin + 1) {
      for (status <- statusTypes) {
        craftWithAddOn(craftStep, StatusAddOn(status, decorator))
      }
    } else if (craftStep.itemTypeRoot.getLevel == gameConfig.getLevelMin + 2) {
      for (bonus <- bonusTypes) {
        craftWithAddOn(craftStep, BonusAddOn(bonus, decorator))
      }
    } else if (craftStep.itemTypeRoot.getLevel == gameConfig.getLevelMin + 3) {
      for (bonus <- bonusTypes) {
        craftWithAddOn(craftStep, BonusAddOn(bonus, decorator))
      }
    }
  }

  def generateCraft(crafts: Crafts[TItemType]): Crafts[TItemType] = {
    val materialPool = new MaterialPool(itemTypeFactory)
    for (natureCategory <- natureTypes) {
      val categoryRoot = new CategoryBuilder[TItemType](natureCategory)
      natureCategory match {
        case Charm(_) => // a charm should not have charm add-ons...
        case _ => categoryRoot.withAddOn(CharmSlotAddOn[TItemType](decorator))
      }
      val itemTypeRoot = categoryRoot.create(gameConfig.getLevelMin)
      itemTypeRoot.setName(categoryRoot.createDescription.getDescription)
      craftItemType(new CraftStep(itemTypeRoot, categoryRoot, crafts, materialPool))
    }
    crafts
  }

}
