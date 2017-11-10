import item._
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

  "A weapon" should "be a weapon" in {
    assert(Item.createWeapon("w", 41).getSlotTypeRequirement == WEAPON_SLOT())
    assert(Item.isWeapon(Item.createWeapon("w", 42)))
  }

  "An armor" should "be an armor" in {
    assert(Item.isArmor(Item.createArmor("a", 42, ArmorPart.HEAD)))
  }

  "A charm" should "be a charm" in {
    assert(Item.isCharm(Item.createCharm("c", 42)))
  }

}
