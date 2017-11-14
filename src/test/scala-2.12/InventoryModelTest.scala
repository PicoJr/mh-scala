import item.{InventoryModel, Item}
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
    inventory.addItems(new Item("item"))
    assert(inventory.getItems.nonEmpty)
  }

  it should "provide damage when a weapon is equipped" in {
    val inventory = new InventoryModel
    val weapon = Item.createWeapon("weapon", 42)
    inventory.addItems(weapon)
    inventory.equipItem(weapon.getUniqueID)
    assert(inventory.getRawDamageProvided > 0)
  }

}
