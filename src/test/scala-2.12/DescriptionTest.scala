import game.description.DescriptionFactory
import game.item.{Item, RandomItemTypeFactory}
import game.unit.Hunter
import org.scalatest.FlatSpec

/**
  * Created by nol on 11/11/17.
  */
class DescriptionTest extends FlatSpec {

  it should "give an item description" in {
    val item = new Item(RandomItemTypeFactory.createWeaponType(1))
    println(DescriptionFactory.description(item))
  }

  it should "give a hunter description" in {
    val hunter = new Hunter("name")
    println(DescriptionFactory.description(hunter))
  }

}
