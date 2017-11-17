import game.Config
import game.item.{InventoryModel, Item, ItemType}
import org.scalatest.FlatSpec

/**
  * Created by nol on 11/11/17.
  */
class InventoryModelTest extends FlatSpec {
  "A new Inventory" must "be empty" in {
    assert(new InventoryModel().getItems.isEmpty)
  }

  it should "have items when items are added" in {
    val inventory = new InventoryModel
    inventory.addItems(new Item(new ItemType("name", Config.LEVEL_MIN)))
    assert(inventory.getItems.nonEmpty)
  }

  it should "provide damage when a weapon is equipped" in {
    val inventory = new InventoryModel
    val weapon = new Item(ItemType.createWeapon("weapon", Config.LEVEL_MIN, 42))
    inventory.addItems(weapon)
    inventory.equipItem(weapon.getUniqueId)
    assert(inventory.getRawDamageProvided > 0)
  }

}
