package game.item.craft

import game.config.ConfigLoader
import game.item._
import game.item.craft.bonus.{BonusType, DAMAGE, PROTECTION}
import game.item.craft.nature.{ARMOR, CHARM, NatureType, WEAPON}
import game.item.element._
import game.item.status.{NEUTRAL, SLEEP, STUN, StatusType}

/** Default craft factory.
  * FIXME: not very Open-Close friendly.
  * Created by nol on 20/11/17.
  */
class DefaultCraftFactory(bonusTypes: Seq[BonusType],
                          elementTypes: Seq[ElementType],
                          natureTypes: Seq[NatureType],
                          statusTypes: Seq[StatusType]
                         ) {

  def this() {
    this(
      Seq(DAMAGE, PROTECTION),
      Seq(ELECTRIC, FIRE, NORMAL, WATER),
      Seq(WEAPON, ARMOR(ArmorPart.HEAD), ARMOR(ArmorPart.BODY), ARMOR(ArmorPart.ARMS), ARMOR(ArmorPart.LEGS), CHARM),
      Seq(NEUTRAL, SLEEP, STUN)
    )
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
      for (element <- elementTypes) {
        craftWithAddOn(craftStep, ElementAddOn(element))
      }
    } else if (craftStep.itemTypeRoot.getLevel == DefaultCraftFactory.config.getLevelMin + 1) {
      for (status <- statusTypes) {
        craftWithAddOn(craftStep, StatusAddOn(status))
      }
    } else if (craftStep.itemTypeRoot.getLevel == DefaultCraftFactory.config.getLevelMin + 2) {
      for (bonus <- bonusTypes) {
        craftWithAddOn(craftStep, BonusAddOn(bonus))
      }
    } else if (craftStep.itemTypeRoot.getLevel == DefaultCraftFactory.config.getLevelMin + 3) {
      for (bonus <- bonusTypes) {
        craftWithAddOn(craftStep, BonusAddOn(bonus))
      }
    }
  }

  def generateCraft: Crafts = {
    val crafts = new DefaultCrafts
    val materialPool = new MaterialPool
    for (natureCategory <- natureTypes) {
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
