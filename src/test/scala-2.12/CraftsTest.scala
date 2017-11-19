import game.config.ConfigLoader
import game.item.craft.Crafts
import org.scalatest.FlatSpec

/**
  * Created by nol on 15/11/17.
  */
class CraftsTest extends FlatSpec {

  private val config = ConfigLoader.loadGameConfig

  private val crafts = Crafts.generateCraftRecipes

  "Crafts" must "have materials for each level" in {
    for (level <- config.getLevelMin to config.getLevelMax) {
      assert(crafts.getMaterials(level).nonEmpty, s"level $level")
    }
  }
}
