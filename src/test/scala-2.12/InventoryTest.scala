import game.config.DefaultGameConfig
import game.item._
import game.item.inventory.DefaultInventory
import org.scalatest.FlatSpec

/**
  * Created by nol on 11/11/17.
  */
class InventoryTest extends FlatSpec {
  private val gameConfig = DefaultGameConfig.getGameConfig
  private val itemFactory = DefaultItemFactory.getDefaultItemFactory
  private val itemTypeFactory = DefaultItemTypeFactory.getDefaultItemFactory

  "A new Inventory" must "be empty" in {
    assert(new DefaultInventory().getItems.isEmpty)
  }

  it should "have items when items are added" in {
    val inventory = new DefaultInventory
    inventory.addItems(itemFactory.createItem(new DefaultItemType("name", gameConfig.getLevelMin, 0)))
    assert(inventory.getItems.nonEmpty)
  }

  "Equipped items" should "be detected as equipped" in {
    val inventory = new DefaultInventory
    val item = itemFactory.createItem(itemTypeFactory.createWeapon(gameConfig.getLevelMin, 42))
    assert(!inventory.isEquipped(item))
    inventory.addItems(item)
    inventory.tryEquipItem(item.getUniqueId, force = false)
    assert(inventory.isEquipped(item))
  }

  it should "provide damage when a weapon is equipped" in {
    val inventory = new DefaultInventory
    val weapon = itemFactory.createItem(itemTypeFactory.createWeapon(gameConfig.getLevelMin, 42))
    inventory.addItems(weapon)
    inventory.tryEquipItem(weapon.getUniqueId, force = false)
    assert(inventory.getDamageProvided > 0)
  }

  it should "provide armor when an armor is equipped" in {
    val inventory = new DefaultInventory
    val armor = itemFactory.createItem(itemTypeFactory.createArmor(gameConfig.getLevelMin, 42, ArmorPart.HEAD))
    inventory.addItems(armor)
    inventory.tryEquipItem(armor.getUniqueId, force = false)
    assert(inventory.getArmorProvided > 0)
  }

  it should "not equip item that cannot be equipped" in {
    val inventory = new DefaultInventory
    val material = itemFactory.createItem(itemTypeFactory.createMaterial("cannot be equipped", gameConfig.getLevelMin))
    inventory.addItems(material)
    inventory.tryEquipItem(material.getUniqueId, force = true)
    assert(!inventory.isEquipped(material))
  }

}
