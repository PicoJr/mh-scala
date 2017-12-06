import game.item.craft.DefaultCraftFactory
import org.scalatest.FlatSpec

/**
  * Created by nol on 20/11/17.
  */
class DefaultCraftFactoryTest extends FlatSpec {

  it should "generate non empty crafts" in {
    val crafts = new DefaultCraftFactory().generateCraft
    assert(crafts.getRecipes.nonEmpty)
  }

}
