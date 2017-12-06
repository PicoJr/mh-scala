package game.item.craft

import game.config.ConfigLoader
import game.item._
import game.item.craft.bonus.{BonusType, DefaultBonusTypeEnum}
import game.item.craft.nature.{CHARM, DefaultNatureTypeEnum, NatureType}
import game.item.element.{DefaultElementTypeEnum, ElementType}
import game.item.status.{DefaultStatusTypeEnum, StatusType}

/**
  * Created by nol on 20/11/17.
  */
class DefaultCraftFactory(bonusTypeEnum: OpenEnum[BonusType],
                          elementTypeEnum: OpenEnum[ElementType],
                          natureTypeEnum: OpenEnum[NatureType],
                          statusTypeEnum: OpenEnum[StatusType]
                         ) extends CraftFactory {

  def this() {
    this(new DefaultBonusTypeEnum, new DefaultElementTypeEnum, new DefaultNatureTypeEnum, new DefaultStatusTypeEnum)
  }

  case class CraftStep(itemTypeRoot: ItemType, categoryRoot: CategoryBuilder, crafts: Crafts, materialPool: MaterialPool)

  private def craftItemType(craftStep: CraftStep): Unit = {
    def craftWithAddOn(craftStep: CraftStep, addOn: AddOn): Unit = {
      val resultCategory = craftStep.categoryRoot.copy.withAddOn(addOn)
      val result = resultCategory.createItemType(craftStep.itemTypeRoot.getLevel + 1)
      val resultDescription = resultCategory.createDescription
      result.setName(resultDescription.getDescription)
      val material = craftStep.materialPool.getMaterial(addOn, craftStep.itemTypeRoot.getLevel)
      craftStep.crafts.addRecipe(craftStep.itemTypeRoot, material, result)
      craftItemType(CraftStep(result, resultCategory, craftStep.crafts, craftStep.materialPool))
    }

    if (craftStep.itemTypeRoot.getLevel == DefaultCraftFactory.config.getLevelMin) {
      for (element <- elementTypeEnum.getValues) {
        craftWithAddOn(craftStep, ElementAddOn(element))
      }
    } else if (craftStep.itemTypeRoot.getLevel == DefaultCraftFactory.config.getLevelMin + 1) {
      for (status <- statusTypeEnum.getValues) {
        craftWithAddOn(craftStep, StatusAddOn(status))
      }
    } else if (craftStep.itemTypeRoot.getLevel == DefaultCraftFactory.config.getLevelMin + 2) {
      for (bonus <- bonusTypeEnum.getValues) {
        craftWithAddOn(craftStep, BonusAddOn(bonus))
      }
    } else if (craftStep.itemTypeRoot.getLevel == DefaultCraftFactory.config.getLevelMin + 3) {
      for (bonus <- bonusTypeEnum.getValues) {
        craftWithAddOn(craftStep, BonusAddOn(bonus))
      }
    }
  }

  def generateCraft: Crafts = {
    val crafts = new DefaultCrafts
    val materialPool = new MaterialPool
    for (natureCategory <- natureTypeEnum.getValues) {
      val categoryRoot = (new CategoryBuilder).withNature(natureCategory)
      natureCategory match {
        case CHARM => // a charm should not have charm add-ons...
        case _ => categoryRoot.withAddOn(CharmSlotAddOn)
      }
      val itemTypeRoot = categoryRoot.createItemType(DefaultCraftFactory.config.getLevelMin)
      itemTypeRoot.setName(categoryRoot.createDescription.getDescription)
      craftItemType(CraftStep(itemTypeRoot, categoryRoot, crafts, materialPool))
    }
    crafts
  }

}

private object DefaultCraftFactory {
  private val config = ConfigLoader.loadGameConfig
}
