import game.config.ConfigLoader
import game.item._
import org.scalatest.FlatSpec

/**
  * Created by nol on 11/11/17.
  */
class InventoryTest extends FlatSpec {
  private val config = ConfigLoader.loadGameConfig

  "A new Inventory" must "be empty" in {
    assert(new DefaultInventory().getItems.isEmpty)
  }

  it should "have items when items are added" in {
    val inventory = new DefaultInventory
    inventory.addItems(DefaultItem.createItem(new DefaultItemType("name", config.getLevelMin)))
    assert(inventory.getItems.nonEmpty)
  }

  "Equipped items" should "be detected as equipped" in {
    val inventory = new DefaultInventory
    val item = DefaultItem.createItem(DefaultItemType.createWeapon(config.getLevelMin, 42))
    assert(!inventory.isEquipped(item))
    inventory.addItems(item)
    inventory.equipItem(item.getUniqueId)
    assert(inventory.isEquipped(item))
  }

  it should "provide damage when a weapon is equipped" in {
    val inventory = new DefaultInventory
    val weapon = new DefaultItem(DefaultItemType.createWeapon(config.getLevelMin, 42))
    inventory.addItems(weapon)
    inventory.equipItem(weapon.getUniqueId)
    assert(inventory.getDamageProvided > 0)
  }

  it should "provide armor when an armor is equipped" in {
    val inventory = new DefaultInventory
    val armor = new DefaultItem(DefaultItemType.createArmor(config.getLevelMin, 42, ArmorPart.HEAD))
    inventory.addItems(armor)
    inventory.equipItem(armor.getUniqueId)
    assert(inventory.getArmorProvided > 0)
  }

  it should "not equip item that cannot be equipped" in {
    val inventory = new DefaultInventory
    val material = new DefaultItem(DefaultItemType.createMaterial("cannot be equipped", config.getLevelMin))
    inventory.addItems(material)
    inventory.equipItem(material.getUniqueId)
    assert(!inventory.isEquipped(material))
  }

}
