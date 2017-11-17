import game.Config
import game.item.craft.Crafts
import org.scalatest.FlatSpec

/**
  * Created by nol on 15/11/17.
  */
class CraftsTest extends FlatSpec {

  private val crafts = Crafts.generateCraftRecipes

  "Crafts" must "have materials for each level" in {
    for (level <- Config.LEVEL_MIN to Config.LEVEL_MAX) {
      assert(crafts.getMaterials(level).nonEmpty, s"level $level")
    }
  }
}
