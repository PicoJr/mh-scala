import game.config.ConfigLoader
import game.item._
import org.scalatest.FlatSpec

/**
  * Created by nol on 08/11/17.
  */
class ItemTest extends FlatSpec {

  private val config = ConfigLoader.loadGameConfig

  "An Item ID" should "remain the same" in {
    val item = new Item(new ItemType("1", config.getLevelMin))
    assert(item.getUniqueId == item.getUniqueId)
  }

  "An Item ID" should "be unique" in {
    val item1 = new Item(new ItemType("1", config.getLevelMin))
    val item2 = new Item(new ItemType("2", config.getLevelMin))
    assert(item1.getUniqueId != item2.getUniqueId)
  }

  "An item" should "be seen as an ItemType instance" in {
    val itemType = new ItemType("1", config.getLevelMin)
    assert(ItemType.createItem(itemType).isItemType(itemType))
  }

  "A weapon" should "be a weapon" in {
    assert(ItemType.createWeapon(42, config.getLevelMin).isWeapon)
  }

  "An armor" should "be an armor" in {
    val armor = ItemType.createArmor(config.getLevelMin, 42, ArmorPart.HEAD)
    assert(armor.isArmor)
    assert(armor.isArmorPartRequired(ArmorPart.HEAD))
  }

  "A charm" should "be a charm" in {
    assert(ItemType.createCharm(config.getLevelMin, 42).isCharm)
  }

}
