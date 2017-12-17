package game.item

import game.id.{DefaultIdSupplier, IdSupplier}
import game.item.ArmorPart.ArmorPart

/**
  * Created by nol on 17/12/17.
  */
class DefaultItemTypeFactory(idSupplier: IdSupplier) extends AbstractItemTypeFactory {
  override def createWeapon(level: Int, damage: Int): ItemType = {
    Damage(Equipment(new DefaultItemType(level, idSupplier.getNextUniqueId), WEAPON_SLOT), damage)
  }

  override def createArmor(level: Int, armor: Int, armorPart: ArmorPart): ItemType = {
    Protection(Equipment(new DefaultItemType(level, idSupplier.getNextUniqueId), ARMOR_SLOT(armorPart)), armor)
  }

  override def createCharm(level: Int, slotsRequired: Int): ItemType = {
    Equipment(new DefaultItemType(level, idSupplier.getNextUniqueId), CHARM_SLOT(slotsRequired))
  }

  override def createMaterial(name: String, level: Int): ItemType = {
    Material(new DefaultItemType(name, level, idSupplier.getNextUniqueId))
  }
}

object DefaultItemTypeFactory {
  private final val idSupplier = new DefaultIdSupplier()
  private final val itemTypeFactory: AbstractItemTypeFactory = new DefaultItemTypeFactory(idSupplier)

  def getDefaultItemFactory: AbstractItemTypeFactory = itemTypeFactory
}
