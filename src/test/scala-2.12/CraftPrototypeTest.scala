import game.config.ConfigLoader
import game.item.craft.CraftPrototype
import org.scalatest.FlatSpec

/**
  * Created by nol on 20/11/17.
  */
class CraftPrototypeTest extends FlatSpec {

  private val config = ConfigLoader.loadGameConfig

  it should "generate non empty crafts" in {
    val crafts = CraftPrototype.generateCraft
    assert(crafts.getRecipes.nonEmpty)
  }

}
