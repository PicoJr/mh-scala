import description.DescriptionBuilder
import item.RandomItemFactory
import org.scalatest.FlatSpec
import unit.Hunter

/**
  * Created by nol on 11/11/17.
  */
class DescriptionTest extends FlatSpec {

  it should "give an item description" in {
    val item = RandomItemFactory.getRandomWeapon(1)
    println(DescriptionBuilder.description(item))
  }

  it should "give a hunter description" in {
    val hunter = new Hunter("name")
    println(DescriptionBuilder.description(hunter))
  }

}
