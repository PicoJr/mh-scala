package game.item.craft

import game.config.ConfigLoader
import game.item._
import game.item.element.ElementType

/**
  * Created by nol on 20/11/17.
  */
object CraftPrototype {

  private val config = ConfigLoader.loadGameConfig

  case class CraftStep(itemTypeRoot: ItemType, categoryRoot: CategoryBuilder, crafts: Crafts, materialPool: MaterialPool)

  def craftItemType(craftStep: CraftStep): Unit = {
    def craftWithAddOn(craftStep: CraftStep, addOn: AddOn): Unit = {
      val resultCategory = craftStep.categoryRoot.copy.withAddOn(addOn)
      val result = resultCategory.createItemType(craftStep.itemTypeRoot.getLevel + 1)
      val resultDescription = resultCategory.createDescription
      result.setName(resultDescription.getDescription)
      val material = craftStep.materialPool.getMaterial(addOn, craftStep.itemTypeRoot.getLevel)
      craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
      craftItemType(CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
    }

    if (craftStep.itemTypeRoot.getLevel == config.getLevelMin) {
      for (element <- ElementType.values) {
        craftWithAddOn(craftStep, ElementAddOn(element))
      }
    } else if (craftStep.itemTypeRoot.getLevel == config.getLevelMin + 1) {
      for (status <- StatusType.values) {
        craftWithAddOn(craftStep, StatusAddOn(status))
      }
    } else if (craftStep.itemTypeRoot.getLevel == config.getLevelMin + 2) {
      for (bonus <- BonusType.values) {
        craftWithAddOn(craftStep, BonusAddOn(bonus))
      }
    }
  }

  def generateCraft: Crafts = {
    val crafts = new DefaultCrafts
    val materialPool = new MaterialPool
    for (natureCategory <- NatureType.values) {
      val categoryRoot = (new CategoryBuilder).withNature(natureCategory)
      natureCategory match {
        case CHARM => // a charm should not have charm add-ons...
        case _ => categoryRoot.withAddOn(CharmSlotAddOn)
      }
      val itemTypeRoot = categoryRoot.createItemType(config.getLevelMin)
      itemTypeRoot.setName(categoryRoot.createDescription.getDescription)
      craftItemType(CraftStep(itemTypeRoot, categoryRoot, crafts, materialPool))
    }
    crafts
  }
}
