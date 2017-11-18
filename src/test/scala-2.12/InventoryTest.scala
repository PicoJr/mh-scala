import game.Config
import game.item.{Inventory, Item, ItemType}
import org.scalatest.FlatSpec

/**
  * Created by nol on 11/11/17.
  */
class InventoryTest extends FlatSpec {
  "A new Inventory" must "be empty" in {
    assert(new Inventory().getItems.isEmpty)
  }

  it should "have items when items are added" in {
    val inventory = new Inventory
    inventory.addItems(new Item(new ItemType("name", Config.LEVEL_MIN)))
    assert(inventory.getItems.nonEmpty)
  }

  it should "provide damage when a weapon is equipped" in {
    val inventory = new Inventory
    val weapon = new Item(ItemType.createWeapon("weapon", Config.LEVEL_MIN, 42))
    inventory.addItems(weapon)
    inventory.equipItem(weapon.getUniqueId)
    assert(inventory.getDamageProvided > 0)
  }

}
