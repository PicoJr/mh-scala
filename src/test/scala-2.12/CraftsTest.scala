import config.Config
import item.Classification
import org.scalatest.FlatSpec
import procedural.Crafts

/**
  * Created by nol on 15/11/17.
  */
class CraftsTest extends FlatSpec {

  private val crafts = Crafts.generateCraftRecipes

  "Materials" should "be generated for each level" in {
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      val materials = Crafts.createMaterials(level)
      assert(materials.nonEmpty)
      assert(materials.forall(i => i.getLevel == level && i.isMaterial))
    }
  }

  "Armors" should "be generated for each level" in {
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      assert(Crafts.createArmors(level).nonEmpty)
    }
  }

  "Last level armors" should "have all classifications enabled" in {
    val lastLevelArmors = Crafts.createArmors(Config.LEVEL_MAX)
    for (armor <- lastLevelArmors) {
      assert(armor.isClassifiedAs(Classification.CHARM_SLOT, Classification.DAMAGE, Classification.ELEMENT, Classification.STATUS))
    }
  }

  "Weapons" should "be generated for each level" in {
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      assert(Crafts.createWeapons(level).nonEmpty)
    }
  }

  "Charms" should "be generated for each level" in {
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      assert(Crafts.createCharms(level).nonEmpty)
    }
  }

  "ItemTypes" should "be generated for each level" in {
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      assert(Crafts.createItemTypes(level).nonEmpty)
    }
  }

  "Crafts" must "have materials for each level" in {
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      assert(crafts.getMaterials(level).nonEmpty, s"level $level")
    }
  }
}
