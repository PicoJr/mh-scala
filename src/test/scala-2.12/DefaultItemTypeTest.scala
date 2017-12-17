import game.config.DefaultGameConfig
import game.item._
import org.scalatest.FlatSpec

/**
  * Created by nol on 08/11/17.
  */
class DefaultItemTypeTest extends FlatSpec {

  private val gameConfig = DefaultGameConfig.getGameConfig

  "An Item ID" should "remain the same" in {
    val item = new DefaultItem(new DefaultItemType("1", gameConfig.getLevelMin))
    assert(item.getUniqueId == item.getUniqueId)
  }

  "An Item ID" should "be unique" in {
    val item1 = new DefaultItem(new DefaultItemType("1", gameConfig.getLevelMin))
    val item2 = new DefaultItem(new DefaultItemType("2", gameConfig.getLevelMin))
    assert(item1.getUniqueId != item2.getUniqueId)
  }

  "An item" should "be seen as an ItemType instance" in {
    val itemType = new DefaultItemType("1", gameConfig.getLevelMin)
    assert(DefaultItem.createItem(itemType).isItemType(itemType))
  }

  "A weapon" should "be a weapon" in {
    assert(DefaultItemType.createWeapon(42, gameConfig.getLevelMin).isWeapon)
  }

  "An armor" should "be an armor" in {
    val armor = DefaultItemType.createArmor(gameConfig.getLevelMin, 42, ArmorPart.HEAD)
    assert(armor.isArmor)
    assert(armor.isArmorPartRequired(ArmorPart.HEAD))
  }

  "An armor" should "provide armor" in {
    val armor = DefaultItemType.createArmor(gameConfig.getLevelMin, 42, ArmorPart.HEAD)
    assert(armor.hasArmor)
  }

  "A weapon" should "provide damage" in {
    val weapon = DefaultItemType.createWeapon(gameConfig.getLevelMin, 42)
    assert(weapon.hasDamage)
  }

  "An equipment" should "be an equipment" in {
    val equipment = Equipment(new DefaultItemType(0), WEAPON_SLOT)
    assert(equipment.isEquipment)
  }

  "An armor" should "also be an equipment" in {
    val armor = DefaultItemType.createArmor(gameConfig.getLevelMin, 42, ArmorPart.HEAD)
    assert(armor.isEquipment)
  }

  "A charm" should "be a charm" in {
    assert(DefaultItemType.createCharm(gameConfig.getLevelMin, 42).isCharm)
  }

}
