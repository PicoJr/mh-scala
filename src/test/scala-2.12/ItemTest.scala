import game.Config
import game.item._
import org.scalatest.FlatSpec

/**
  * Created by nol on 08/11/17.
  */
class ItemTest extends FlatSpec {

  "An Item ID" should "remain the same" in {
    val item = new Item(new ItemType("1", Config.LEVEL_MIN))
    assert(item.getUniqueId == item.getUniqueId)
  }

  "An Item ID" should "be unique" in {
    val item1 = new Item(new ItemType("1", Config.LEVEL_MIN))
    val item2 = new Item(new ItemType("2", Config.LEVEL_MIN))
    assert(item1.getUniqueId != item2.getUniqueId)
  }

  "An item" should "be seen as an ItemType instance" in {
    val itemType = new ItemType("1", Config.LEVEL_MIN)
    assert(ItemType.createItem(itemType).isItemType(itemType))
  }

  "A weapon" should "be a weapon" in {
    assert(ItemType.createWeapon("w", 42, Config.LEVEL_MIN).isWeapon)
  }

  "An armor" should "be an armor" in {
    val armor = ItemType.createArmor("a", Config.LEVEL_MIN, 42, ArmorPart.HEAD)
    assert(armor.isArmor)
    assert(armor.isArmorPartRequired(ArmorPart.HEAD))
  }

  "A charm" should "be a charm" in {
    assert(ItemType.createCharm("c", Config.LEVEL_MIN, 42).isCharm)
  }

  "A random weapon" should "be a weapon" in {
    assert(RandomItemTypeFactory.createWeaponType(Config.LEVEL_MIN).isWeapon)
  }

  "A random armor" should "be an armor" in {
    assert(RandomItemTypeFactory.createArmorType(1, ArmorPart.HEAD).isArmor)
  }

  "A random charm" should "be a charm" in {
    assert(RandomItemTypeFactory.createCharmType(1).isCharm)
  }

  "A random material" should "be a material" in {
    assert(RandomItemTypeFactory.createMaterialType(1).isMaterial)
  }

}
