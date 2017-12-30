import game.config.DefaultGameConfig
import game.id.DefaultIdSupplier
import game.item._
import org.scalatest.FlatSpec

/**
  * Created by nol on 08/11/17.
  */
class DefaultItemTypeTest extends FlatSpec {

  private val gameConfig = DefaultGameConfig.getGameConfig
  private val itemFactory = new DefaultItemFactory(new DefaultIdSupplier)
  private val itemTypeFactory = new DefaultItemTypeFactory(new DefaultIdSupplier)

  "An Item ID" should "remain the same" in {
    val item = itemFactory.createItem(new DefaultItemType("1", gameConfig.getLevelMin, 0))
    assert(item.getUniqueId == item.getUniqueId)
  }

  "A weapon" should "be a weapon" in {
    assert(itemTypeFactory.createWeapon(42, gameConfig.getLevelMin).isWeapon)
  }

  "An armor" should "be an armor" in {
    val armor = itemTypeFactory.createArmor(gameConfig.getLevelMin, 42, ArmorPart.HEAD)
    assert(armor.isArmor)
    assert(armor.isArmorPartRequired(ArmorPart.HEAD))
  }

  "An armor" should "provide armor" in {
    val armor = itemTypeFactory.createArmor(gameConfig.getLevelMin, 42, ArmorPart.HEAD)
    assert(armor.hasArmor)
  }

  "A weapon" should "provide damage" in {
    val weapon = itemTypeFactory.createWeapon(gameConfig.getLevelMin, 42)
    assert(weapon.hasDamage)
  }

  "An equipment" should "be an equipment" in {
    val equipment = Equipment(new DefaultItemType("1", gameConfig.getLevelMin, 0), WEAPON_SLOT)
    assert(equipment.isEquipment)
  }

  "An armor" should "also be an equipment" in {
    val armor = itemTypeFactory.createArmor(gameConfig.getLevelMin, 42, ArmorPart.HEAD)
    assert(armor.isEquipment)
  }

  "A charm" should "be a charm" in {
    assert(itemTypeFactory.createCharm(gameConfig.getLevelMin, 42).isCharm)
  }

}
