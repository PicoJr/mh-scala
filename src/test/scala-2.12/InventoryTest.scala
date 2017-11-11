import item.{Inventory, Item}
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
    inventory.addItems(new Item("item"))
    assert(inventory.getItems.nonEmpty)
  }

  it should "provide damage when a weapon is equipped" in {
    val inventory = new Inventory
    val weapon = Item.createWeapon("weapon", 42)
    weapon.equip()
    inventory.addItems(weapon)
    assert(inventory.getRawDamageProvided > 0)
  }

}
