import item.{Item, ItemDecorator}
import org.scalatest.FlatSpec

/**
  * Created by nol on 08/11/17.
  */
class ItemTest extends FlatSpec {
  "An Item ID" should "be unique" in {
    val item1 = new Item("1")
    val item2 = new Item("2")
    assert(item1.getUniqueID != item2.getUniqueID)
  }

  "A decorated Item ID" should "be unique" in {
    val item = new Item("1")
    val decorated = new ItemDecorator(item)
    val decorated2 = new ItemDecorator(item)
    assert(item.getUniqueID != decorated.getUniqueID)
    assert(decorated.getUniqueID != decorated2.getUniqueID)
  }

}
